/* ==========================================================================
   PAWHUB UNIVERSAL JAVASCRIPT
   Theme switcher, Wishlist manager, PawMatch Quiz & Navigation controller
   ========================================================================== */

(function () {
  'use strict';

  // 1. THEME MANAGER
  const savedTheme = localStorage.getItem('pawhub_theme') || 'light';
  document.documentElement.setAttribute('data-theme', savedTheme);

  window.togglePawTheme = function () {
    const current = document.documentElement.getAttribute('data-theme');
    const next = current === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', next);
    localStorage.setItem('pawhub_theme', next);
    updateThemeIcon();
  };

  function updateThemeIcon() {
    const isDark = document.documentElement.getAttribute('data-theme') === 'dark';
    document.querySelectorAll('.paw-theme-btn').forEach(btn => {
      btn.innerHTML = isDark ? '☀️' : '🌙';
      btn.title = isDark ? 'Switch to Light Mode' : 'Switch to Dark Mode';
    });
  }

  // 2. WISHLIST MANAGER
  let favorites = JSON.parse(localStorage.getItem('pawhub_favorites') || '[]');

  window.toggleFavorite = function (petName, petBreed, petImg) {
    const idx = favorites.findIndex(f => f.name === petName);
    if (idx > -1) {
      favorites.splice(idx, 1);
    } else {
      favorites.push({ name: petName, breed: petBreed, img: petImg });
      spawnHeartAnimation();
    }
    localStorage.setItem('pawhub_favorites', JSON.stringify(favorites));
    updateWishlistUI();
  };

  window.removeFavorite = function (petName) {
    favorites = favorites.filter(f => f.name !== petName);
    localStorage.setItem('pawhub_favorites', JSON.stringify(favorites));
    updateWishlistUI();
  };

  window.openWishlist = function () {
    const overlay = document.getElementById('paw-wishlist-overlay');
    const drawer = document.getElementById('paw-wishlist-drawer');
    if (overlay && drawer) {
      overlay.classList.add('open');
      drawer.classList.add('open');
      renderWishlistItems();
    }
  };

  window.closeWishlist = function () {
    const overlay = document.getElementById('paw-wishlist-overlay');
    const drawer = document.getElementById('paw-wishlist-drawer');
    if (overlay && drawer) {
      overlay.classList.remove('open');
      drawer.classList.remove('open');
    }
  };

  function updateWishlistUI() {
    const badges = document.querySelectorAll('.paw-fav-count');
    badges.forEach(b => {
      b.textContent = favorites.length;
      b.style.display = favorites.length > 0 ? 'inline-block' : 'none';
    });

    // Update heart icons on cards
    document.querySelectorAll('[data-pet-name]').forEach(btn => {
      const name = btn.getAttribute('data-pet-name');
      const isFav = favorites.some(f => f.name === name);
      btn.classList.toggle('active', isFav);
      btn.innerHTML = isFav ? '❤️' : '🤍';
    });

    renderWishlistItems();
  }

  function renderWishlistItems() {
    const list = document.getElementById('paw-wishlist-items');
    if (!list) return;

    if (favorites.length === 0) {
      list.innerHTML = `
        <div style="text-align: center; padding: 40px 10px; color: var(--paw-text-muted);">
          <div style="font-size: 48px; margin-bottom: 12px;">🐾</div>
          <p style="font-weight: 600; font-size: 16px; margin: 0 0 6px 0;">No favorite pets yet!</p>
          <p style="font-size: 13px; margin: 0;">Click the ❤️ on any puppy or kitten to save them here.</p>
        </div>
      `;
      return;
    }

    list.innerHTML = favorites.map(f => `
      <div class="paw-fav-card">
        <img src="${f.img || 'images/puppy1.jpg'}" alt="${f.name}">
        <div class="paw-fav-info">
          <h4>${f.name}</h4>
          <p>${f.breed || 'Adorable Pet'}</p>
        </div>
        <a href="adoption.html" style="padding: 6px 12px; background: var(--paw-primary); color: white; border-radius: 12px; text-decoration: none; font-size: 12px; font-weight: 600;">Adopt</a>
        <button class="paw-fav-remove" onclick="removeFavorite('${f.name}')" title="Remove">&times;</button>
      </div>
    `).join('');
  }

  function spawnHeartAnimation() {
    const heart = document.createElement('div');
    heart.textContent = '💖';
    heart.style.position = 'fixed';
    heart.style.left = '50%';
    heart.style.top = '50%';
    heart.style.fontSize = '32px';
    heart.style.zIndex = '10020';
    heart.style.pointerEvents = 'none';
    heart.style.transition = 'all 0.8s cubic-bezier(0.34, 1.56, 0.64, 1)';
    document.body.appendChild(heart);

    setTimeout(() => {
      heart.style.transform = 'translate(180px, -280px) scale(1.6)';
      heart.style.opacity = '0';
    }, 20);

    setTimeout(() => heart.remove(), 850);
  }

  // 3. PAWMATCH QUIZ CONTROLLER
  window.openPawMatchQuiz = function () {
    const modal = document.getElementById('paw-quiz-modal');
    if (modal) {
      modal.classList.add('open');
      showQuizStep(1);
    }
  };

  window.closePawMatchQuiz = function () {
    const modal = document.getElementById('paw-quiz-modal');
    if (modal) modal.classList.remove('open');
  };

  let quizAnswers = {};

  window.recordQuizAnswer = function (step, value) {
    quizAnswers[step] = value;
    if (step < 3) {
      showQuizStep(step + 1);
    } else {
      calculateQuizResult();
    }
  };

  function showQuizStep(stepNum) {
    document.querySelectorAll('.paw-quiz-step').forEach(s => s.classList.remove('active'));
    const target = document.getElementById(`paw-quiz-step-${stepNum}`);
    if (target) target.classList.add('active');
  }

  function calculateQuizResult() {
    const pets = [
      { name: "Luna", breed: "Beagle", type: "Dog", img: "images/puppy3.jpg", desc: "Curious, affectionate and great for active homes!" },
      { name: "Charlie", breed: "Labrador", type: "Dog", img: "images/puppy2.jpg", desc: "Loyal, playful family dog who adores walks and play." },
      { name: "Milo", breed: "Persian Cat", type: "Cat", img: "images/cat1.jpg", desc: "Fluffy, calm cuddlebug ideal for apartments and quiet spaces." },
      { name: "Snow", breed: "Husky", type: "Dog", img: "images/puppy5.jpg", desc: "Energetic adventurer ready for outdoor marathons!" },
      { name: "Coco", breed: "Ragdoll Cat", type: "Cat", img: "images/cat4.jpg", desc: "Gentle lap cat that loves cuddles, naps and cozy vibes." }
    ];

    let match = pets[1]; // default Labrador
    if (quizAnswers[1] === 'apartment') {
      match = quizAnswers[2] === 'couch' ? pets[2] : pets[4];
    } else if (quizAnswers[2] === 'runner') {
      match = pets[3]; // Husky
    } else if (quizAnswers[2] === 'couch') {
      match = pets[4]; // Ragdoll
    } else {
      match = pets[0]; // Beagle
    }

    const target = document.getElementById('paw-quiz-step-result');
    if (target) {
      target.innerHTML = `
        <div style="font-size: 50px; margin-bottom: 10px;">🎉🐾</div>
        <h3 style="font-size: 24px; color: var(--paw-primary); margin: 0 0 8px 0;">It's a Match!</h3>
        <p style="margin-bottom: 18px; font-size: 15px;">Based on your lifestyle, your paw soulmate is:</p>
        
        <div style="background: var(--paw-bg); border: 2px solid var(--paw-border); border-radius: 18px; padding: 16px; margin-bottom: 20px;">
          <img src="${match.img}" alt="${match.name}" style="width: 110px; height: 110px; border-radius: 14px; object-fit: cover; box-shadow: 0 8px 20px rgba(0,0,0,0.12); margin-bottom: 10px;">
          <h4 style="margin: 0; font-size: 20px; color: var(--paw-text);">${match.name}</h4>
          <span style="font-size: 13px; font-weight: 600; color: var(--paw-primary);">${match.breed} • ${match.type}</span>
          <p style="margin: 8px 0 0 0; font-size: 13px; color: var(--paw-text-muted);">${match.desc}</p>
        </div>

        <div style="display: flex; gap: 10px; justify-content: center;">
          <a href="adoption.html" style="padding: 12px 24px; background: linear-gradient(135deg, #2563eb, #1d4ed8); color: white; border-radius: 14px; text-decoration: none; font-weight: 700; font-size: 14px;">Adopt ${match.name} Now →</a>
          <button type="button" onclick="showQuizStep(1)" style="padding: 12px 18px; border: 1px solid var(--paw-border); background: var(--paw-card-bg); color: var(--paw-text); border-radius: 14px; font-weight: 600; cursor: pointer;">Retake Quiz</button>
        </div>
      `;
      showQuizStep('result');
      triggerConfetti();
    }
  }

  function triggerConfetti() {
    const colors = ['#2563eb', '#10b981', '#f59e0b', '#f43f5e', '#8b5cf6'];
    for (let i = 0; i < 45; i++) {
      const c = document.createElement('div');
      c.className = 'paw-confetti';
      c.style.left = Math.random() * 100 + 'vw';
      c.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
      c.style.animationDuration = (Math.random() * 1.5 + 1.5) + 's';
      c.style.animationDelay = (Math.random() * 0.3) + 's';
      c.style.width = (Math.random() * 8 + 6) + 'px';
      c.style.height = (Math.random() * 8 + 6) + 'px';
      c.style.borderRadius = Math.random() > 0.5 ? '50%' : '2px';
      document.body.appendChild(c);
      setTimeout(() => c.remove(), 3200);
    }
  }

  // 4. MOBILE DRAWER
  window.togglePawMobile = function () {
    const drawer = document.getElementById('paw-mobile-drawer');
    if (drawer) drawer.classList.toggle('open');
  };

  // INITIALIZE ON LOAD
  window.addEventListener('DOMContentLoaded', () => {
    updateThemeIcon();
    updateWishlistUI();

    // Highlight current nav item
    const currentPath = window.location.pathname.split('/').pop() || 'home.html';
    document.querySelectorAll('.paw-nav-item a').forEach(a => {
      const href = a.getAttribute('href');
      if (href === currentPath || (currentPath === '' && href === 'home.html') || (currentPath === 'index.html' && href === 'home.html')) {
        a.parentElement.classList.add('active');
      }
    });
  });

})();
