'use strict';

self.addEventListener('install', function(event) {
  self.skipWaiting();
  console.log('Installed', event);
});

self.addEventListener('activate', function(event) {
  console.log('Activated', event);
});

self.addEventListener('push', function(event) {
  console.log('Message Received', event);

  let message = "";
  if (event.data) {
    message = event.data.text();
  } else {
    message = 'Demo Message';
  }

  event.waitUntil(
    self.registration.showNotification("webpush-scala", {
      body: message,
      tag: 'demo-tag'
    })
  );
});
