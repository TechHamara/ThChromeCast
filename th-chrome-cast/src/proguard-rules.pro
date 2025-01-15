# Repackages optimized classes into io.th.thchromecast.thchromecast.repacked package in resulting
# AIX. Repackaging is necessary to avoid clashes with the other extensions that
# might be using same libraries as you.

-repackageclasses io.th.thchromecast.thchromecast.repacked

-keep class org.slf4j.** { *; }
-keep interface org.slf4j.** { *; }

-keep class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**