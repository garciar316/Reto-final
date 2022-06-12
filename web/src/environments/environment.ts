// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  baseUrl: 'https://questions-back-final.herokuapp.com/',
  emailSenderUrl: 'https://server-email-sender.herokuapp.com/',
  firebaseConfig : {
    apiKey: "AIzaSyDsTXFWsYXkIOhObah8IFuS7XSpKfVVS1Y",
    authDomain: "reto-final-a24b6.firebaseapp.com",
    projectId: "reto-final-a24b6",
    storageBucket: "reto-final-a24b6.appspot.com",
    messagingSenderId: "154402833676",
    appId: "1:154402833676:web:67ddf22b66ac0bbe657529"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
