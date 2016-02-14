# Prefs
Simple Java Preferences (**JSON based**) with static access!

[![](https://jitpack.io/v/FrancisCan/Prefs.svg)](https://jitpack.io/#FrancisCan/Prefs)

## Get it!
### Gradle
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    compile 'com.github.FrancisCan:Prefs:1.0'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.FrancisCan</groupId>
        <artifactId>Prefs</artifactId>  
        <version>1.0</version>
    </dependency>
</dependencies>
```

###  Download JAR
[Release 1.0](https://github.com/FrancisCan/Prefs/releases)

## Init
Initialize Prefs on start
```java
new Prefs().init();

// or

new Prefs("MyCustomName").init();
```

## Put / Get
Integer, Float, Double, String, Boolean

```java

// Put values

Prefs.putInt("key", 10);

Prefs.putFloat("key", 10f);

Prefs.putDouble("key", 10d);

Prefs.putBoolean("key", true);

Prefs.putString("key", "Test");

// Get values

int i = Prefs.getInt("key", 0);

float f = Prefs.getFloat("key", 0f);

double d = Prefs.getDouble("key", 0d);

boolean b = Prefs.getBoolean("key", 0d);

String s = Prefs.getString("key", null);
```

Serializable Object
```java
class MyObj implements Serializable {
    ...
}

Prefs.putObject("key", new MyObj());

MyObj o = Prefs.getObject("key", new MyObj());
```

## Remove

```java
Prefs.remove("key");

// specific class for multiple key
Prefs.remove("key", Boolean.class);

```

## Iterator
Iterate all keys of specific type (ex. all String , Integers etc...)
```java
Iterator<Item<String>> it = Prefs.iterator(String.class);

while (it.hasNext())
  System.out.println(it.next().value);

```

## License
MIT [Francesco Cannizzaro](https://github.com/FrancisCan)
