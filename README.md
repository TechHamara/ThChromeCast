## ThChromeCast
Chromecast Extension to interact with Chromecast devices

<div align="center">
<h1><kbd>🧩 ThChromeCast</kbd></h1>
An extension for MIT App Inventor 2.<br>
Developed by th using Fast.
</div>

## Library Usage 
https://github.com/vitalidze/chromecast-java-api-v2

## 📝 Specifications
* **
💾 **Size:** 4.20 MB
⚙️ **Version:** 1.0
📱 **Minimum API Level:** 7
📅 **Updated On:** [date=2025-01-15 timezone="Indian/Maldives"]
💻 **Built & documented using:** [FAST-CLI](https://community.appinventor.mit.edu/t/fast-an-efficient-way-to-build-extensions/129103?u=jewel)

## <kbd>Events:</kbd>
**ThChromeCast** has total 13 events.

### 💛 OnPermissionsGranted
Triggered when all required permissions are granted.

### 💛 OnPermissionsDenied
Triggered when permission request is denied.

### 💛 OnConnected
Triggered when connected to a Chromecast device.

| Parameter | Type
| - | - |
| ipAddress | text

### 💛 OnDisconnected
Triggered when disconnected from a Chromecast device.

### 💛 OnMediaLoaded
Triggered when media is successfully loaded.

### 💛 OnPlaybackPaused
Triggered when playback is paused.

### 💛 OnPlaybackStopped
Triggered when playback is stopped.

### 💛 OnVolumeChanged
Triggered when the volume is changed.

| Parameter | Type
| - | - |
| volume | number

### 💛 ErrorOccurred
Triggered when an error occurs.

| Parameter | Type
| - | - |
| errorMessage | text

### 💛 OnAppLaunched
Triggered when an app is launched on the Chromecast.

| Parameter | Type
| - | - |
| appId | text

### 💛 OnMediaSeeked
Triggered when media is seeked to a specific time.

| Parameter | Type
| - | - |
| time | number

### 💛 OnDiscoveryStarted
Triggered when discovery is started.

### 💛 OnDiscoveryStopped
Triggered when discovery is stopped.

## <kbd>Methods:</kbd>
**ThChromeCast** has total 26 methods.

### 💜 RequestPermissions
Request required permissions dynamically.

### 💜 SetName
Sets the technical name of the device.

| Parameter | Type
| - | - |
| name | text

### 💜 SetAppsURL
Sets the URL for accessing apps on the device.

| Parameter | Type
| - | - |
| appsURL | text

### 💜 SetApplication
Sets the mDNS service name.

| Parameter | Type
| - | - |
| application | text

### 💜 AddDevice
Manually add Chromecast device by its IP address.

| Parameter | Type
| - | - |
| ipAddress | text

### 💜 GetDevices
Get the list of added devices.

### 💜 Connect
Connect to a Chromecast by its IP address.

| Parameter | Type
| - | - |
| ipAddress | text

### 💜 Disconnect
Disconnect from the current Chromecast.

### 💜 PlayMedia
Play media on the connected Chromecast.

| Parameter | Type
| - | - |
| mediaUrl | text
| contentType | text

### 💜 PausePlayback
Pause playback on the connected Chromecast.

### 💜 StopPlayback
Stop playback on the connected Chromecast.

### 💜 SetVolume
Set the volume on the connected Chromecast.

| Parameter | Type
| - | - |
| volume | number

### 💜 Status
Get the current status of the connected Chromecast.

### 💜 RunningApp
Get the running app on the connected Chromecast.

### 💜 IsAppAvailable
Check if an app is available on the connected Chromecast.

| Parameter | Type
| - | - |
| appId | text

### 💜 IsAppRunning
Check if an app is running on the connected Chromecast.

| Parameter | Type
| - | - |
| appId | text

### 💜 LaunchApp
Launch an app on the connected Chromecast. id like (233637DE)

| Parameter | Type
| - | - |
| appId | text

### 💜 SeekMedia
Seek media playback to a specific time.

| Parameter | Type
| - | - |
| time | number

### 💜 StartDiscovery
Start discovering Chromecast devices on the network.

### 💜 StopDiscovery
Stop discovering Chromecast devices on the network.

### 💜 DiscoveredDevices
Get the list of discovered Chromecast devices.

### 💜 RestartDiscovery
Restart Discovery

### 💜 SetAutoReconnect
Changes behavior for opening/closing connection with ChromeCast device.

| Parameter | Type
| - | - |
| autoReconnect | boolean

### 💜 StopApp
Stops currently running application. Throws exception if no application is running.

### 💜 StopSession
Stops the session with the given identifier.

| Parameter | Type
| - | - |
| sessionId | text

### 💜 SetVolume2
Sets the volume level from 0 to 1.

| Parameter | Type
| - | - |
| level | number

## <kbd>Designer:</kbd>
**ThChromeCast** has total 1 designer property.

### 🍏 AppID

* Input type: `string`

## <kbd>Setter:</kbd>
**ThChromeCast** has total 1 setter property.

### 💚 AppID
Set app ID.

* Input type: `text`

## <kbd>Getters:</kbd>
**ThChromeCast** has total 9 getter properties.

### 🟢 Name
Returns the technical name of the device.

* Return type: `text`

### 🟢 Address
Returns the IP address of the device.

* Return type: `text`

### 🟢 Port
Returns the TCP port number (like 8009) that the device is listening to.

* Return type: `number`

### 🟢 AppsURL
Returns the URL for accessing apps on the device.

* Return type: `text`

### 🟢 Application
Returns the mDNS service name, usually 'googlecast'.

* Return type: `text`

### 🟢 Title
Returns the name of the device as entered by the person who installed it.

* Return type: `text`

### 🟢 AppTitle
Returns the title of the app that is currently running, or empty string in case of the backdrop.

* Return type: `text`

### 🟢 Model
Returns the model of the device. Usually 'Chromecast' or, if Chromecast is built into your TV, the model of your TV.

* Return type: `text`

### 🟢 IsAutoReconnect
Returns the current value of the autoReconnect setting.

* Return type: `boolean`

