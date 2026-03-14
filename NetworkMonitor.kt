package com.example.networkmonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData

/**
 * Network Monitor - Monitors LTE RRC state and network parameters
 */
class NetworkMonitor(private val context: Context) {

    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val rrcState = MutableLiveData<String>()
    val signalStrength = MutableLiveData<String>()
    val networkType = MutableLiveData<String>()
    val dataState = MutableLiveData<String>()

    private var phoneStateListener: PhoneStateListener? = null

    /**
     * Start monitoring network changes
     */
    fun startMonitoring() {
        phoneStateListener = object : PhoneStateListener() {
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                super.onSignalStrengthsChanged(signalStrength)
                updateSignalStrength(signalStrength)
            }

            override fun onDataConnectionStateChanged(state: Int) {
                super.onDataConnectionStateChanged(state)
                updateDataState(state)
            }
        }

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS or PhoneStateListener.LISTEN_DATA_CONNECTION_STATE)
        updateNetworkType()
        updateRrcState()
    }

    /**
     * Stop monitoring network changes
     */
    fun stopMonitoring() {
        phoneStateListener?.let {
            telephonyManager.listen(it, PhoneStateListener.LISTEN_NONE)
        }
    }

    /**
     * Get current LTE RRC state
     * Returns: RRC_IDLE, RRC_CONNECTED, or UNKNOWN
     */
    private fun updateRrcState() {
        val rrc = when {
            isDataActive() -> "RRC_CONNECTED"
            else -> "RRC_IDLE"
        }
        rrcState.postValue(rrc)
    }

    /**
     * Update signal strength in dBm
     */
    private fun updateSignalStrength(strength: SignalStrength) {
        val dbm = try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                strength.cellSignalStrengths[0].dbm
            } else {
                strength.gsmSignalStrength * 2 - 113
            }
        } catch (e: Exception) {
            0
        }
        signalStrength.postValue("$dbm dBm")
    }

    /**
     * Update current network type (LTE, 5G, Wi-Fi, etc.)
     */
    private fun updateNetworkType() {
        val type = try {
            val activeNetwork = connectivityManager.activeNetwork ?: return
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return

            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    when (telephonyManager.networkType) {
                        TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
                        TelephonyManager.NETWORK_TYPE_NR -> "5G"
                        TelephonyManager.NETWORK_TYPE_UMTS -> "3G (UMTS)"
                        TelephonyManager.NETWORK_TYPE_EDGE -> "2G (EDGE)"
                        else -> "Cellular"
                    }
                }
                else -> "Unknown"
            }
        } catch (e: Exception) {
            "Unknown"
        }
        networkType.postValue(type)
    }

    /**
     * Update data connection state
     */
    private fun updateDataState(state: Int) {
        val dataState = when (state) {
            TelephonyManager.DATA_CONNECTED -> "Connected"
            TelephonyManager.DATA_CONNECTING -> "Connecting"
            TelephonyManager.DATA_DISCONNECTED -> "Disconnected"
            TelephonyManager.DATA_SUSPENDED -> "Suspended"
            else -> "Unknown"
        }
        this.dataState.postValue(dataState)
    }

    /**
     * Check if data is currently active
     */
    private fun isDataActive(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            connectivityManager.getNetworkCapabilities(activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Refresh all network parameters
     */
    fun refresh() {
        updateNetworkType()
        updateRrcState()
    }
}