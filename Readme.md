# Firebase Cloud Messaging / Android Things Pager

My sample of an [Android Things](https://developer.android.com/things/index.html) app, that will display a Google [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/) (FCM) notification on an alphanumeric segment display ([Rainbow HAT](https://shop.pimoroni.com/products/rainbow-hat-for-android-things)) as scrolling text (marquee). Written by a guy that's not that into Android development and pattern, yet.

### Prerequisites

* Android Things Developer Preivrew 6 or higher
* Raspberry Pi Rainbow HAT
* Firebase account

### Scheme

A FCM notification will be send to the registered Android Things app. The app receive it and will route the contained information via internal intent broadcasts to the `MainActivity`. The activity will display the text on the display by using Runnables.

![Scheme](https://github.com/tscholze/java-android-things-firebase-pager/blob/master/docs/scheme.png "Scheme")

### How to run

* Install Android Things on your Raspberry Pi
* Install and test the Rainbow HAT
* Enable the FCM feature on your Firebase account and embedd the `google-services.json` file into the project
* Deploy app to Raspberry Pi
* Copy FCM token (Log output) or copy the packge identifer
* Go to the FCM console and send a notification
* If everything works as expected: The notications body text will be displayed on the segment display

## Publications
- Personal Blog: [[Android Things] Google Firebase Cloud Messaging Pager mit Android Things](https://tscholze.uber.space/2018/01/02/android-google-firebase-cloud-messaging-pager-mit-android-things/)
- Hackster.io: (Firebase Cloud Messaging / Android Things Pager](https://www.hackster.io/tscholze/firebase-cloud-messaging-android-things-pager-c07e64)


## Contributing

This is a one-time-example, there will be no further development (maybe platform verison bumps fixes). That means, no contribution is necessary.

## Authors

Just me, [Tobi]([https://tscholze.github.io).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
Dependencies or assets maybe licensed differently.

## Acknowledgments

* [JD Koren](https://github.com/jdkoren) for providing some inspiration how to implement a running text
* [Google's IoT Developers Community](https://plus.google.com/u/0/communities/107507328426910012281) for helping beginners, too.
