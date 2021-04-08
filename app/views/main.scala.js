@(encodedPublicKey: String)

var subscription = null;

const onClickedRegister = function() {
  console.log('register ServiceWorker');
  navigator.serviceWorker.register('/sw.js').then(function() {
    Notification.requestPermission(function(permission) {
      if (permission !== 'granted') {
        return Promise.reject(new Error('bad permission result permission => '+permission))
      } else {
        document.querySelector('button.subscribe').disabled = false;
        return Promise.resolve();
      }
    })
  }).catch(function(err) {
    console.error(err);
  });
};

const onClickedSubscribe = function() {
  console.log('Subscribe');

  const publicKey = new Uint8Array([@encodedPublicKey])

  navigator.serviceWorker.ready
    .then(function(reg) {
      return reg.pushManager.subscribe({
        applicationServerKey: publicKey,
        userVisibleOnly: true
      });
    })
    .then(function(sub) {
      setSubscription(sub);
    })
    .catch(function(err) {
      console.error(err);
      resetSubscription();
    });
}

const onClickedUnsubscribe = function() {
  console.log('Unsubscribe');
  if (subscription) {
    subscription.unsubscribe();
  }
  resetSubscription();
}

window.onload = function() {
  document.querySelector('button.register').addEventListener('click', onClickedRegister);
  document.querySelector('button.subscribe').addEventListener('click', onClickedSubscribe);
  document.querySelector('button.unsubscribe').addEventListener('click', onClickedUnsubscribe);

  if ('serviceWorker' in navigator) {
    console.log('Service Worker is supported');

    navigator.serviceWorker.getRegistration()
      .then(function(reg){
        if (typeof reg === 'undefined') {
          document.querySelector("button.register").disabled = false;
        }
      });

    navigator.serviceWorker.ready
      .then(function(sw) {
        return sw.pushManager.getSubscription();
      }).then(function(sub) {
        setSubscription(sub);
      })
      .catch(function(err) {
        console.error(err);
        resetSubscription();
      });
  } else {
    console.error(new Error("Service Workers aren't supported in this browser"))
  }
};

const setSubscription = function(sub) {
  if (!sub) {
    resetSubscription();
  } else {
    subscription = sub;

    document.querySelector("button.register").disabled = true;
    document.querySelector('button.subscribe').disabled = true;
    document.querySelector('button.unsubscribe').disabled = false;

    const registrationId = subscription.endpoint.match(/^.*\/(.*)$/)[1];

    const endpoint = subscription.endpoint;
    const mybtoa = function(str) {
      return btoa(String.fromCharCode.apply(null, new Uint8Array(str))).replace(/\+/g, '-').replace(/\//g, '_');
    };
    const auth = mybtoa(subscription.getKey('auth'));
    const p256dh = mybtoa(subscription.getKey('p256dh'));

    let platform = "unknown";
    const ua = window.navigator.userAgent.toLowerCase();
    if (ua.indexOf("chrome") !== -1) {
      platform = "chrome";
    } else if (ua.indexOf("firefox") !== -1) {
      platform = "firefox";
    } else {
      console.error(new Error("failed to detect supported browser, support browsers => firefox, chrome"));
      return;
    }

    document.querySelector('#endpoint').value = endpoint;
    document.querySelector('#user_auth').value = auth;
    document.querySelector('#user_public_key').value = p256dh;

    document.querySelector('#submit_by_curl').innerHTML =
`curl -H "Content-type: application/json" \\
-X POST https://webpush-scala.herokuapp.com \\
-d '{
  "payload" : "${$("#payload").val()}",
  "endpoint" : "${endpoint}",
  "publicKey" : "${p256dh}",
  "userAuth" : "${auth}",
  "ttl" : ${$("#ttl").val()}
}'`

  }
}

const resetSubscription = function() {
  subscription = null;
  document.querySelector('button.register').disabled = true;
  document.querySelector('button.subscribe').disabled = false;
  document.querySelector('button.unsubscribe').disabled = true;

  document.querySelector('#endpoint').value = "";
  document.querySelector('#user_auth').value = "";
  document.querySelector('#user_public_key').value = "";
  document.querySelector('#submit_by_curl').innerHTML = "";
}

$('#submit_webpush_request').click(function() {
  const json = {
    payload: $("#payload").val(),
    endpoint: $("#endpoint").val(),
    publicKey: $("#user_public_key").val(),
    userAuth: $("#user_auth").val(),
    ttl: Number($("#ttl").val())
  };

  console.log(json);

  $.post({
    url: "@webpush.routes.Main.send",
    data: JSON.stringify(json),
    contentType: 'application/JSON',
    success: function(result, textStatus, xhr) {
      console.log("success " + textStatus + " " + result);
    },
    error: function(xhr, textStatus, error) {
      console.error(error);
    }
  });
});

$(function () {
  const clipboard = new ClipboardJS('.btn');
});
