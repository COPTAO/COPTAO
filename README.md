# Android Network Monitor

A lightweight Android application to monitor LTE RRC state and network parameters in real-time.

## 📋 Features

- **LTE RRC State Detection**: Monitor RRC_IDLE and RRC_CONNECTED states
- **Signal Strength Monitoring**: Real-time signal strength in dBm
- **Network Type Detection**: Identify 5G, LTE, 3G, 2G, and Wi-Fi connections
- **Data Connection State**: Track data connection status (connected, connecting, disconnected, suspended)
- **Real-time Updates**: Automatic monitoring with live data updates
- **Manual Refresh**: Button to manually refresh network parameters

## 🛠️ Technology Stack

- **Language**: Kotlin
- **Platform**: Android 5.1+ (API Level 21+)
- **Architecture**: MVVM with LiveData
- **Build System**: Gradle

## 📦 Dependencies

```gradle
androidx.core:core-ktx:1.10.0
androidx.appcompat:appcompat:1.6.1
androidx.lifecycle:lifecycle-runtime-ktx:2.6.2
androidx.telephony:telephony:1.0.0
```

## 🔐 Required Permissions

```xml
android.permission.ACCESS_NETWORK_STATE
android.permission.READ_PHONE_STATE
android.permission.ACCESS_COARSE_LOCATION
android.permission.ACCESS_FINE_LOCATION
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK Level 33+
- Kotlin 1.8+

### Installation

1. Clone or download this project
2. Open in Android Studio
3. Sync Gradle files
4. Connect an Android device (API 21+)
5. Run the application

### Build from Command Line

```bash
./gradlew build
./gradlew installDebug
```

## 📱 Usage

1. Launch the app on your Android device
2. Grant required permissions when prompted
3. View real-time network parameters:
   - LTE RRC State (RRC_IDLE or RRC_CONNECTED)
   - Signal Strength (in dBm)
   - Current Network Type (LTE, 5G, Wi-Fi, etc.)
   - Data Connection State
4. Tap "Refresh" to manually update all parameters

## 📊 Monitoring Details

### RRC State
- **RRC_IDLE**: Device is idle, radio link not in use
- **RRC_CONNECTED**: Device is actively connected to the network

### Signal Strength
- Measured in dBm (decibels relative to one milliwatt)
- Range typically: -140 dBm (poor) to -50 dBm (excellent)
- LTE typical range: -140 to -40 dBm

### Network Types
- **5G**: Fifth generation mobile network
- **LTE**: Fourth generation (4G) mobile network
- **3G (UMTS)**: Universal Mobile Telecommunications System
- **2G (EDGE)**: Enhanced Data Rates for GSM Evolution
- **Wi-Fi**: Wireless LAN connection

## 🔄 Architecture

### NetworkMonitor Class
Core monitoring logic:
- Registers PhoneStateListener for network changes
- Updates LiveData objects with real-time values
- Provides refresh functionality

### MainActivity
UI layer:
- Displays network parameters in TextViews
- Manages permissions
- Handles observer updates

### LiveData Integration
- Real-time UI updates without manual refresh
- Lifecycle-aware data handling
- Thread-safe updates

## 📝 Code Structure

```
src/main/
├── kotlin/com/example/networkmonitor/
│   ├── MainActivity.kt          # Main UI Activity
│   ├── NetworkMonitor.kt        # Core monitoring logic
├── res/
│   ├── layout/
│   │   └── activity_main.xml   # Main layout
│   ├── values/
│   │   └── strings.xml         # String resources
├── AndroidManifest.xml          # App configuration
```

## 🔧 Configuration

Edit `build.gradle.kts` to modify:
- Target SDK version
- Minimum SDK version
- Application ID
- Version numbers

## 🐛 Troubleshooting

### No data showing
- Check that all permissions are granted
- Ensure device has active data connection
- Try clicking "Refresh" button

### Permissions not requested
- App may have already been granted/denied permissions
- Clear app data and reinstall
- Check Settings > Apps > Network Monitor > Permissions

### Constant RRC_IDLE state
- This is normal behavior when device is not actively transmitting data
- Try downloading data or opening a network-dependent app
- RRC state updates based on network activity

## 📚 Additional Resources

- [Android Telephony API](https://developer.android.com/reference/android/telephony)
- [Android Connectivity](https://developer.android.com/guide/topics/connectivity)
- [LiveData Documentation](https://developer.android.com/topic/libraries/architecture/livedata)

## 📄 License

This project is provided as-is for educational and monitoring purposes.

## 👨‍💻 Author

COPTAO - Network Monitoring Tools

---

**Note**: This app requires actual device with cellular radio. Emulator support is limited
