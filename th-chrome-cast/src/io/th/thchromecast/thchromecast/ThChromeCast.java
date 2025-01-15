package io.th.thchromecast.thchromecast;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;
import android.content.Context;
import java.util.List;

import java.io.IOException;
import android.os.Looper;
import java.util.List;
import android.net.Uri;
import android.view.View;
import java.io.IOException;
import android.os.Handler;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;

import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView;

import android.graphics.*;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import android.graphics.RenderEffect;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Matrix;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

import android.os.Handler;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import su.litvak.chromecast.api.v2.*;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.Media;
import java.util.ArrayList;
import java.util.List;
import java.security.GeneralSecurityException;

import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.MediaStatus;
import su.litvak.chromecast.api.v2.Status;
import su.litvak.chromecast.api.v2.Media;
import su.litvak.chromecast.api.v2.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@DesignerComponent(
	version = 45,
	versionName = "1.0",
	description = "Developed by th using Fast.",
	iconName = "icon.png"
)
public class ThChromeCast extends AndroidNonvisibleComponent {
    
    private List<String> deviceAddresses = new ArrayList<>();
    private ChromeCast chromeCast;
    private boolean isConnected = false;
    private static String APP_ID = "";
    private String name;
    private final String address;
    private final int port;
    private String appsURL;
    private String application;
    
    private boolean autoReconnect = true;

    private String title;
    private String appTitle;
    private String model;
    
  public ThChromeCast(ComponentContainer container) {
    super(container.$form());
    this.address = "default_address"; // Replace with actual default value
        this.port = 8009; // Replace with actual default value
        this.chromeCast = null;
  }

@SimpleFunction(description = "Request required permissions dynamically.")
public void RequestPermissions() {
    // Get the current activity context
    Activity activity = (Activity) this.form;
    
    // List of permissions to request
    String[] permissions = {
        "android.permission.INTERNET",
        "android.permission.ACCESS_WIFI_STATE",
        "android.permission.ACCESS_NETWORK_STATE",
        "android.permission.READ_EXTERNAL_STORAGE"
    };
    
    // Check if permissions are already granted
    boolean allPermissionsGranted = true;
    for (String permission : permissions) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            allPermissionsGranted = false;
            break;
        }
    }

    // If permissions are not granted, request them
    if (!allPermissionsGranted) {
        ActivityCompat.requestPermissions(activity, permissions, 1);
    } else {
        // Permissions are already granted
        OnPermissionsGranted();
    }
}

@SimpleEvent(description = "Triggered when all required permissions are granted.")
public void OnPermissionsGranted() {
    EventDispatcher.dispatchEvent(this, "OnPermissionsGranted");
}

@SimpleEvent(description = "Triggered when permission request is denied.")
public void OnPermissionsDenied() {
    EventDispatcher.dispatchEvent(this, "OnPermissionsDenied");
}
    
    @DesignerProperty(
      editorType = "string",
      defaultValue = ""
   )
   @SimpleProperty(category = PropertyCategory.BEHAVIOR,
description = "Set app ID.")
   public void AppID(String appID) {
      if (appID.equals("DEFAULT_MEDIA_RECEIVER_APPLICATION_ID")) {
         APP_ID = "CC1AD845";
      } else {
         APP_ID = appID;
      }

   }
   
    @SimpleProperty(description = "Returns the technical name of the device.")
    public final String Name() {
        return name;
    }

    @SimpleFunction(description = "Sets the technical name of the device.")
    public final void SetName(String name) {
        this.name = name;
    }

  /*
     * @return The IP address of the device.
    */
    @SimpleProperty(description = "Returns the IP address of the device.")
    public final String Address() {
        return address;
    }

   /*
     * @return The TCP port number that the device is listening to.
    */
    @SimpleProperty(description = "Returns the TCP port number (like 8009) that the device is listening to.")
    public final int Port() {
        return port;
    }

    @SimpleProperty(description = "Returns the URL for accessing apps on the device.")
    public final String AppsURL() {
        return appsURL;
    }

    @SimpleFunction(description = "Sets the URL for accessing apps on the device.")
    public final void SetAppsURL(String appsURL) {
        this.appsURL = appsURL;
    }

  /*
     * @return The mDNS service name. Usually "googlecast".
     *
     * @see #getRunningApp()
  */
    @SimpleProperty(description = "Returns the mDNS service name, usually 'googlecast'.")
    public final String Application() {
        return application;
    }

    @SimpleFunction(description = "Sets the mDNS service name.")
    public final void SetApplication(String application) {
        this.application = application;
    }

  /*
     * @return The name of the device as entered by the person who installed it.
     * Usually something like "Living Room Chromecast".
 */
    @SimpleProperty(description = "Returns the name of the device as entered by the person who installed it.")
    public final String Title() {
        return title;
    }

    /*
     * @return The title of the app that is currently running, or empty string in case of the backdrop.
     * Usually something like "YouTube" or "Spotify", but could also be, say, the URL of a web page being mirrored.
  */
    @SimpleProperty(description = "Returns the title of the app that is currently running, or empty string in case of the backdrop.")
    public final String AppTitle() {
        return appTitle;
    }

    
     
    @SimpleProperty(description = "Returns the model of the device. Usually 'Chromecast' or, if Chromecast is built into your TV, the model of your TV.")
    public final String Model() {
        return model;
    }
    

    @SimpleFunction(description = "Manually add Chromecast device by its IP address.")
    public void AddDevice(String ipAddress) {
        if (!deviceAddresses.contains(ipAddress)) {
            deviceAddresses.add(ipAddress);
        }
    }

    @SimpleFunction(description = "Get the list of added devices.")
    public List<String> GetDevices() {
        return deviceAddresses;
    }

@SimpleFunction(description = "Connect to a Chromecast by its IP address.")
public void Connect(String ipAddress) {
    if (chromeCast != null) {
        Disconnect();
    }
    try {
        chromeCast = new ChromeCast(ipAddress);
        chromeCast.connect();
        isConnected = true;
        OnConnected(ipAddress);
    } catch (IOException | GeneralSecurityException e) {
        ErrorOccurred("Connection failed: " + e.getMessage());
    }
}

    @SimpleEvent(description = "Triggered when connected to a Chromecast device.")
    public void OnConnected(String ipAddress) {
        EventDispatcher.dispatchEvent(this, "OnConnected", ipAddress);
    }

    @SimpleFunction(description = "Disconnect from the current Chromecast.")
    public void Disconnect() {
        if (chromeCast != null && isConnected) {
            try {
                chromeCast.disconnect();
                isConnected = false;
                OnDisconnected();
            } catch (IOException e) {
                ErrorOccurred("Disconnection failed: " + e.getMessage());
            }
        }
    }

    @SimpleEvent(description = "Triggered when disconnected from a Chromecast device.")
    public void OnDisconnected() {
        EventDispatcher.dispatchEvent(this, "OnDisconnected");
    }

@SimpleFunction(description = "Play media on the connected Chromecast.")
public void PlayMedia(String mediaUrl, String contentType) {
    if (chromeCast == null || !isConnected) {
        ErrorOccurred("Not connected to any Chromecast.");
        return;
    }
    try {
        // Define the duration (optional, you can set it to null if not available)
        Double duration = null; // Set the duration of the media if you have it, else null

        // Create the Media object with the required fields only
        Media media = new Media(
                mediaUrl,        // Media URL
                contentType,     // MIME type
                duration,        // Duration (optional, null if not available)
                null,            // Stream type (set to null if not available)
                null,            // Metadata (optional)
                null,            // Custom data (optional)
                null,            // Custom data (optional)
                null             // List of Tracks (optional)
        );

        // Load the media onto the Chromecast
        chromeCast.load(media); 
        OnMediaLoaded();
    } catch (IOException e) {
        ErrorOccurred("Media loading failed: " + e.getMessage());
    }
}

    @SimpleEvent(description = "Triggered when media is successfully loaded.")
    public void OnMediaLoaded() {
        EventDispatcher.dispatchEvent(this, "OnMediaLoaded");
    }

    @SimpleFunction(description = "Pause playback on the connected Chromecast.")
    public void PausePlayback() {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return;
        }
        try {
            chromeCast.pause();
            OnPlaybackPaused();
        } catch (IOException e) {
            ErrorOccurred("Pause failed: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Triggered when playback is paused.")
    public void OnPlaybackPaused() {
        EventDispatcher.dispatchEvent(this, "OnPlaybackPaused");
    }

    @SimpleFunction(description = "Stop playback on the connected Chromecast.")
    public void StopPlayback() {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return;
        }
        try {
            chromeCast.stopApp();
            OnPlaybackStopped();
        } catch (IOException e) {
            ErrorOccurred("Stop failed: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Triggered when playback is stopped.")
    public void OnPlaybackStopped() {
        EventDispatcher.dispatchEvent(this, "OnPlaybackStopped");
    }

    @SimpleFunction(description = "Set the volume on the connected Chromecast.")
    public void SetVolume(float volume) {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return;
        }
        try {
            chromeCast.setVolume(volume);
            OnVolumeChanged(volume);
        } catch (IOException e) {
            ErrorOccurred("Volume change failed: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Triggered when the volume is changed.")
    public void OnVolumeChanged(float volume) {
        EventDispatcher.dispatchEvent(this, "OnVolumeChanged", volume);
    }

    @SimpleEvent(description = "Triggered when an error occurs.")
    public void ErrorOccurred(String errorMessage) {
        EventDispatcher.dispatchEvent(this, "ErrorOccurred", errorMessage);
    }
    


    @SimpleFunction(description = "Get the current status of the connected Chromecast.")
    public String Status() {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return null;
        }
        try {
            Status status = chromeCast.getStatus();
            return status.toString();
        } catch (IOException e) {
            ErrorOccurred("Failed to get status: " + e.getMessage());
            return null;
        }
    }

    @SimpleFunction(description = "Get the running app on the connected Chromecast.")
    public String RunningApp() {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return null;
        }
        try {
            Application app = chromeCast.getRunningApp();
            return app != null ? app.toString() : null;
        } catch (IOException e) {
            ErrorOccurred("Failed to get running app: " + e.getMessage());
            return null;
        }
    }

    @SimpleFunction(description = "Check if an app is available on the connected Chromecast.")
    public boolean IsAppAvailable(String appId) {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return false;
        }
        try {
            return chromeCast.isAppAvailable(appId);
        } catch (IOException e) {
            ErrorOccurred("Failed to check app availability: " + e.getMessage());
            return false;
        }
    }

    @SimpleFunction(description = "Check if an app is running on the connected Chromecast.")
    public boolean IsAppRunning(String appId) {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return false;
        }
        try {
            return chromeCast.isAppRunning(appId);
        } catch (IOException e) {
            ErrorOccurred("Failed to check if app is running: " + e.getMessage());
            return false;
        }
    }

    @SimpleFunction(description = "Launch an app on the connected Chromecast. id like (233637DE)")
    public void LaunchApp(String appId) {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return;
        }
        try {
            chromeCast.launchApp(appId);
            OnAppLaunched(appId);
        } catch (IOException e) {
            ErrorOccurred("Failed to launch app: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Triggered when an app is launched on the Chromecast.")
    public void OnAppLaunched(String appId) {
        EventDispatcher.dispatchEvent(this, "OnAppLaunched", appId);
    }

    @SimpleFunction(description = "Seek media playback to a specific time.")
    public void SeekMedia(double time) {
        if (chromeCast == null || !isConnected) {
            ErrorOccurred("Not connected to any Chromecast.");
            return;
        }
        try {
            chromeCast.seek(time);
            OnMediaSeeked(time);
        } catch (IOException e) {
            ErrorOccurred("Failed to seek media: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Triggered when media is seeked to a specific time.")
    public void OnMediaSeeked(double time) {
        EventDispatcher.dispatchEvent(this, "OnMediaSeeked", time);
    }
    
    @SimpleFunction(description = "Start discovering Chromecast devices on the network.")
    public void StartDiscovery() {
        try {
            ChromeCasts.startDiscovery();
            OnDiscoveryStarted();
        } catch (IOException e) {
            ErrorOccurred("Discovery start failed: " + e.getMessage());
        }
    }

    @SimpleFunction(description = "Stop discovering Chromecast devices on the network.")
    public void StopDiscovery() {
        try {
            ChromeCasts.stopDiscovery();
            OnDiscoveryStopped();
        } catch (IOException e) {
            ErrorOccurred("Discovery stop failed: " + e.getMessage());
        }
    }

    @SimpleFunction(description = "Get the list of discovered Chromecast devices.")
    public List<String> DiscoveredDevices() {
        List<String> discoveredDevices = new ArrayList<>();
        for (ChromeCast device : ChromeCasts.get()) {
            discoveredDevices.add(device.getAddress());
        }
        return discoveredDevices;
    }
    
    @SimpleEvent(description = "Triggered when discovery is started.")
    public void OnDiscoveryStarted() {
        EventDispatcher.dispatchEvent(this, "OnDiscoveryStarted");
    }

    @SimpleEvent(description = "Triggered when discovery is stopped.")
    public void OnDiscoveryStopped() {
        EventDispatcher.dispatchEvent(this, "OnDiscoveryStopped");
    }
    

    // * Restarts the discovery of ChromeCast devices.
     @SimpleFunction(description = "Restart Discovery")
    public static void RestartDiscovery() throws IOException {
        ChromeCasts.stopDiscovery();
        ChromeCasts.startDiscovery();
    }
    

    @SimpleFunction(description = "Changes behavior for opening/closing connection with ChromeCast device.")
    public void SetAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
    
    @SimpleProperty(description = "Returns the current value of the autoReconnect setting.")
    public boolean IsAutoReconnect() {
        return autoReconnect;
    }

@SimpleFunction(description = "Stops currently running application. Throws exception if no application is running.")
public void StopApp() throws IOException {
    if (chromeCast == null || !isConnected) {
        ErrorOccurred("Not connected to any Chromecast.");
        return;
    }
    Application runningApp = chromeCast.getRunningApp();
    if (runningApp == null) {
        throw new IOException("No application is running on Chromecast.");
    }
    chromeCast.stopApp();
}

@SimpleFunction(description = "Stops the session with the given identifier.")
public void StopSession(String sessionId) throws IOException {
    if (chromeCast == null || !isConnected) {
        ErrorOccurred("Not connected to any Chromecast.");
        return;
    }
    chromeCast.stopSession(sessionId);
}

@SimpleFunction(description = "Sets the volume level from 0 to 1.")
public void SetVolume2(float level) throws IOException {
    if (chromeCast == null || !isConnected) {
        ErrorOccurred("Not connected to any Chromecast.");
        return;
    }
    chromeCast.setVolume(level);
}



}
