plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
}

android {
	namespace = "ddwu.com.mobile.project"
	compileSdk = 34

	defaultConfig {
		applicationId = "ddwu.com.mobile.project"
		minSdk = 23
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}

	viewBinding {
		enable = true
	}
}

dependencies {
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	implementation("androidx.recyclerview:recyclerview:1.3.2")
	implementation("androidx.viewpager2:viewpager2:1.0.0")
	implementation("androidx.cardview:cardview:1.0.0")

	// RoomDB
	implementation ("androidx.room:room-ktx:2.6.1")
	implementation ("androidx.room:room-runtime:2.6.1")
	kapt ("androidx.room:room-compiler:2.6.1")

	//Retrofit
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

	//okHttp
	implementation ("com.squareup.okhttp3:okhttp:4.9.0")
	implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
	implementation ("androidx.multidex:multidex:2.0.1")

	//Glide
	implementation ("com.github.bumptech.glide:glide:4.16.0")
	implementation("androidx.core:core-ktx:1.12.0")
	annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

	// GSON
	implementation ("com.google.code.gson:gson:2.9.0")

	implementation ("com.google.android.gms:play-services-maps:18.2.0")
	implementation ("com.google.maps.android:places-ktx:3.0.0")
}