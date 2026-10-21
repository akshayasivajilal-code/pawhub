/* ==========================================================================
   PAWHUB GLOBAL JAVASCRIPT
   Authentication Guard, Theme Toggle, Wishlist, PawMatch Quiz, Nav Active Link
   ========================================================================== */
(function () {
  'use strict';

  // ── 0. AUTHENTICATION & ACCESS GUARD ──────────────────────────────────────
  var publicPages = ['index.html', 'login.html', 'register.html', ''];
  var currentFile = window.location.pathname.split('/').pop() || 'index.html';

  function getCurrentUser() {
    try {
      var raw = localStorage.getItem('loggedInUser');
      return raw ? JSON.parse(raw) : null;
    } catch (e) {
      return null;
    }
  }

  function isLoggedIn() {
    var hasFlag = localStorage.getItem('pawhub_logged_in') === 'true';
    var user = getCurrentUser();
    return hasFlag && user !== null;
  }

  // Access Guard: If visiting a protected page without logging in -> redirect to login.html
  if (!publicPages.includes(currentFile)) {
    if (!isLoggedIn()) {
      sessionStorage.setItem('pawhub_redirect_after_login', currentFile);
      window.location.replace('login.html');
      return;
    }
  }

  window.logoutPawHub = function () {
    if (confirm('Are you sure you want to log out of PAWHUB? 🐾')) {
      localStorage.removeItem('loggedInUser');
      localStorage.removeItem('pawhub_logged_in');
      sessionStorage.removeItem('pawhub_redirect_after_login');
      window.location.replace('login.html');
    }
  };

  // ── 1. THEME ──────────────────────────────────────────────────────────────
  function applyTheme(theme) {
    if (theme === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark');
    } else {
      document.documentElement.removeAttribute('data-theme');
    }
    refreshThemeIcons(theme === 'dark');
  }

  function refreshThemeIcons(isDark) {
    document.querySelectorAll('.theme-icon-sun').forEach(function (el) {
      el.style.display = isDark ? 'block' : 'none';
    });
    document.querySelectorAll('.theme-icon-moon').forEach(function (el) {
      el.style.display = isDark ? 'none' : 'block';
    });
    document.querySelectorAll('.paw-theme-btn').forEach(function (btn) {
      btn.title = isDark ? 'Switch to Light Mode' : 'Switch to Dark Mode';
    });
  }

  window.togglePawTheme = function () {
    var isDark = document.documentElement.getAttribute('data-theme') === 'dark';
    var next = isDark ? 'light' : 'dark';
    localStorage.setItem('pawhub_theme', next);
    applyTheme(next);
  };

  var savedTheme = localStorage.getItem('pawhub_theme') || 'light';
  applyTheme(savedTheme);

  // ── 2. DOM CONTENT LOADED ─────────────────────────────────────────────────
  document.addEventListener('DOMContentLoaded', function () {
    // Highlight current nav link
    document.querySelectorAll('.pawhub-link').forEach(function (link) {
      var href = link.getAttribute('href') || '';
      if (href === currentFile || (currentFile === '' && href === 'home.html') || (currentFile === 'index.html' && href === 'home.html')) {
        link.classList.add('active');
      }
    });

    // Update user display in navbar if logged in
    var user = getCurrentUser();
    var navUserName = document.getElementById('navUserName');
    if (navUserName) {
      if (user) {
        var displayName = user.name ? user.name.split(' ')[0] : (user.email ? user.email.split('@')[0] : 'User');
        navUserName.textContent = displayName + ' 🚪';
      } else {
        navUserName.textContent = 'Sign In';
      }
    }

    // Clean any example phone numbers from session
    if (user && (user.phone === '+91 99999 88888' || user.phone === '+91 98765 43210' || user.phone === '99999 88888' || user.phone === '98765 43210')) {
      user.phone = '';
      try { localStorage.setItem('loggedInUser', JSON.stringify(user)); } catch (e) {}
    }
    // Also sanitize the demo account in the registered users list
    try {
      var regUsers = JSON.parse(localStorage.getItem('pawhub_registered_users') || '[]');
      var regChanged = false;
      regUsers.forEach(function(ru) {
        if (ru.phone === '+91 99999 88888' || ru.phone === '+91 98765 43210') {
          ru.phone = '';
          regChanged = true;
        }
      });
      if (regChanged) { localStorage.setItem('pawhub_registered_users', JSON.stringify(regUsers)); }
    } catch (e) {}

    refreshThemeIcons(document.documentElement.getAttribute('data-theme') === 'dark');
    updateWishlistUI();
  });

  // ── 3. MOBILE TOGGLE ──────────────────────────────────────────────────────
  window.togglePawMobile = function () {
    var menu = document.getElementById('pawMobileMenu');
    if (!menu) return;
    menu.style.display = (menu.style.display === 'none' || menu.style.display === '') ? 'flex' : 'none';
  };

  // ── 4. WISHLIST ───────────────────────────────────────────────────────────
  var favorites = [];
  try {
    favorites = JSON.parse(localStorage.getItem('pawhub_favorites') || '[]');
  } catch (e) {
    favorites = [];
  }

  function updateWishlistUI() {
    var badge = document.querySelector('.paw-fav-count');
    if (!badge) return;
    badge.textContent = favorites.length;
    badge.style.display = favorites.length > 0 ? 'flex' : 'none';
  }

  window.toggleFavorite = function (petName, petBreed, petImg) {
    var idx = favorites.findIndex(function (f) { return f.name === petName; });
    if (idx > -1) {
      favorites.splice(idx, 1);
    } else {
      favorites.push({ name: petName, breed: petBreed, img: petImg });
      spawnHeartAnimation();
    }
    localStorage.setItem('pawhub_favorites', JSON.stringify(favorites));
    updateWishlistUI();
  };

  function spawnHeartAnimation() {
    var heart = document.createElement('div');
    heart.textContent = '❤️';
    heart.style.cssText = 'position:fixed; right:60px; bottom:60px; font-size:32px; z-index:9999; pointer-events:none; animation:pawHeartFloat 1.2s ease-out forwards;';
    document.body.appendChild(heart);
    setTimeout(function () { heart.remove(); }, 1300);
  }

  window.openWishlist = function () {
    var overlay = document.getElementById('paw-wishlist-overlay');
    var drawer = document.getElementById('paw-wishlist-drawer');
    var list = document.getElementById('paw-wishlist-items');
    if (!drawer) return;

    if (list) {
      if (favorites.length === 0) {
        list.innerHTML = '<div style="text-align:center; padding:40px 10px; color:#64748b;">🐾 No saved pets yet.<br><small>Click ❤️ on pets to add them here!</small></div>';
      } else {
        list.innerHTML = favorites.map(function (f, i) {
          return '<div style="display:flex; align-items:center; gap:12px; padding:10px; border-bottom:1px solid #e2e8f0;">' +
                 '<img src="' + (f.img || 'images/dog1.png') + '" style="width:50px; height:50px; border-radius:10px; object-fit:cover;" onerror="this.src=\'images/dog1.png\'">' +
                 '<div style="flex:1;"><b>' + f.name + '</b><br><small style="color:#64748b;">' + (f.breed || 'Pet') + '</small></div>' +
                 '<button onclick="removeFavorite(' + i + ')" style="background:none; border:none; color:#ef4444; font-size:18px; cursor:pointer;" title="Remove">✕</button>' +
                 '</div>';
        }).join('');
      }
    }

    if (overlay) overlay.style.display = 'block';
    drawer.style.right = '0';
  };

  window.closeWishlist = function () {
    var overlay = document.getElementById('paw-wishlist-overlay');
    var drawer = document.getElementById('paw-wishlist-drawer');
    if (drawer) drawer.style.right = '-380px';
    if (overlay) overlay.style.display = 'none';
  };

  window.removeFavorite = function (index) {
    favorites.splice(index, 1);
    localStorage.setItem('pawhub_favorites', JSON.stringify(favorites));
    updateWishlistUI();
    openWishlist();
  };

  // ── 5. PAWMATCH QUIZ ──────────────────────────────────────────────────────
  var quizState = { step: 1, answers: {} };

  window.openPawMatchQuiz = function () {
    var modal = document.getElementById('paw-quiz-modal');
    if (!modal) return;
    quizState = { step: 1, answers: {} };
    showQuizStep(1);
    modal.style.display = 'flex';
  };

  window.closePawMatchQuiz = function () {
    var modal = document.getElementById('paw-quiz-modal');
    if (modal) modal.style.display = 'none';
  };

  window.recordQuizAnswer = function (step, value) {
    quizState.answers['step' + step] = value;
    if (step < 3) {
      showQuizStep(step + 1);
    } else {
      showQuizResult();
    }
  };

  function showQuizStep(step) {
    for (var i = 1; i <= 3; i++) {
      var el = document.getElementById('paw-quiz-step-' + i);
      if (el) el.style.display = (i === step) ? 'block' : 'none';
    }
    var resEl = document.getElementById('paw-quiz-step-result');
    if (resEl) resEl.style.display = 'none';
  }

  function showQuizResult() {
    for (var i = 1; i <= 3; i++) {
      var el = document.getElementById('paw-quiz-step-' + i);
      if (el) el.style.display = 'none';
    }
    var resEl = document.getElementById('paw-quiz-step-result');
    if (!resEl) return;

    var space = quizState.answers.step1;
    var matchName = 'Bella the Golden Retriever';
    var matchDesc = 'Playful, affectionate, and great with families!';
    var matchImg = 'images/dog1.png';

    if (space === 'apartment') {
      matchName = 'Milo the French Bulldog';
      matchDesc = 'Calm, compact, and perfectly happy in an apartment!';
      matchImg = 'images/dog2.png';
    } else if (space === 'farm') {
      matchName = 'Rocky the German Shepherd';
      matchDesc = 'Active, loyal, and thrives with lots of open space!';
      matchImg = 'images/dog3.png';
    }

    resEl.innerHTML = '<div style="text-align:center; padding:20px 10px;">' +
      '<div style="font-size:42px; margin-bottom:10px;">🎉</div>' +
      '<h3 style="font-size:22px; color:#1e293b; margin:0 0 8px;">Your Perfect Paw Match!</h3>' +
      '<img src="' + matchImg + '" style="width:140px; height:140px; border-radius:24px; object-fit:cover; margin:14px auto; box-shadow:0 8px 24px rgba(0,0,0,0.12);" onerror="this.src=\'images/dog1.png\'">' +
      '<h4 style="font-size:18px; color:#2563eb; margin:6px 0;">' + matchName + '</h4>' +
      '<p style="font-size:13.5px; color:#64748b; margin:0 0 18px;">' + matchDesc + '</p>' +
      '<div style="display:flex; gap:10px; justify-content:center;">' +
      '<a href="adoption.html" style="background:#2563eb; color:#fff; padding:10px 20px; border-radius:12px; text-decoration:none; font-weight:700; font-size:13.5px;">Meet Your Match 🐾</a>' +
      '<button onclick="closePawMatchQuiz()" style="background:#f1f5f9; color:#475569; border:none; padding:10px 16px; border-radius:12px; cursor:pointer; font-weight:600; font-size:13.5px;">Close</button>' +
      '</div></div>';
    resEl.style.display = 'block';
  }

})();
