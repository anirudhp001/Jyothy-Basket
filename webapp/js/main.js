document.addEventListener('DOMContentLoaded', () => {
    fetchSessionInfo();
    initCustomCursor();
    initScrollAnimations();
});

function fetchSessionInfo() {
    fetch('SessionServlet')
        .then(response => response.json())
        .then(data => {
            const nav = document.getElementById('nav-links');
            if (!nav) return;
            
            nav.innerHTML = `
                <a href="index.html"><span>Home</span></a>
                <a href="cart.html"><span>Cart</span></a>
            `;
            
            if (data.loggedIn) {
                nav.innerHTML += `
                    <span class="welcome-text">Hi, ${data.username}!</span>
                    <a href="portfolio.html"><span>Portfolio</span></a>
                    <a href="LogoutServlet" class="btn-logout" style="margin-left: 1rem;">Logout</a>
                `;
            } else {
                nav.innerHTML += `
                    <a href="login.html"><span>Login</span></a>
                    <a href="register.html" class="btn-register" style="margin-left: 1rem;"><span>Register</span></a>
                `;
            }
        })
        .catch(err => console.error('Session fetch error:', err));
}

/* --- STRINGTUNE: CUSTOM DYNAMIC CURSOR --- */
function initCustomCursor() {
    if (window.innerWidth <= 768) return; // No cursor on mobile
    
    const dot = document.createElement('div');
    dot.className = 'cursor-dot';
    const ring = document.createElement('div');
    ring.className = 'cursor-ring';
    
    document.body.appendChild(dot);
    document.body.appendChild(ring);
    
    let mouseX = window.innerWidth / 2;
    let mouseY = window.innerHeight / 2;
    let ringX = mouseX;
    let ringY = mouseY;
    
    document.addEventListener('mousemove', (e) => {
        mouseX = e.clientX;
        mouseY = e.clientY;
        
        // Dot follows instantly
        dot.style.transform = `translate(${mouseX}px, ${mouseY}px) translate(-50%, -50%)`;
    });
    
    // Ring follows with easing
    function renderCursor() {
        ringX += (mouseX - ringX) * 0.15;
        ringY += (mouseY - ringY) * 0.15;
        ring.style.transform = `translate(${ringX}px, ${ringY}px) translate(-50%, -50%)`;
        requestAnimationFrame(renderCursor);
    }
    requestAnimationFrame(renderCursor);
    
    // Hover states for links and buttons
    const interactiveElements = document.querySelectorAll('a, button, input, .product-card');
    interactiveElements.forEach(el => {
        el.addEventListener('mouseenter', () => document.body.classList.add('cursor-hover'));
        el.addEventListener('mouseleave', () => document.body.classList.remove('cursor-hover'));
    });
    
    // Add listener to dynamically added cards
    const observer = new MutationObserver((mutations) => {
        mutations.forEach((mutation) => {
            if (mutation.addedNodes.length) {
                mutation.addedNodes.forEach(node => {
                    if (node.nodeType === 1 && (node.tagName === 'A' || node.tagName === 'BUTTON' || node.classList.contains('product-card'))) {
                        node.addEventListener('mouseenter', () => document.body.classList.add('cursor-hover'));
                        node.addEventListener('mouseleave', () => document.body.classList.remove('cursor-hover'));
                    }
                });
            }
        });
    });
    
    const container = document.getElementById('product-container');
    if (container) {
        observer.observe(container, { childList: true });
    }
}

/* --- STRINGTUNE: SCROLL ANIMATIONS (Intersection Observer) --- */
function initScrollAnimations() {
    // Reveal text logic (split string into spans) - simple version of -s-char/-s-word
    const splitElements = document.querySelectorAll('.split-text');
    
    splitElements.forEach(el => {
        const text = el.innerText;
        el.innerHTML = '';
        const words = text.split(' ');
        
        words.forEach(word => {
            const wordSpan = document.createElement('span');
            wordSpan.className = 'split-word';
            wordSpan.style.marginRight = '0.3em';
            
            ([...word]).forEach((char, i) => {
                const charSpan = document.createElement('span');
                charSpan.className = 'split-char';
                charSpan.innerText = char;
                charSpan.style.transitionDelay = `${(i * 0.03)}s`;
                wordSpan.appendChild(charSpan);
            });
            el.appendChild(wordSpan);
        });
    });

    // Intersection observer for both split text AND product cards
    const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
    };
    
    const observer = new IntersectionObserver((entries, obs) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('is-inview');
                obs.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    document.querySelectorAll('.split-text, .product-card').forEach(el => {
        observer.observe(el);
    });
    
    // Dynamic observer for injected product cards
    const gridObserver = new MutationObserver((mutations) => {
        mutations.forEach(mutation => {
            mutation.addedNodes.forEach(node => {
                if (node.nodeType === 1 && node.classList.contains('product-card')) {
                    observer.observe(node);
                    // Add slight base delay based on child index
                    const index = Array.from(node.parentNode.children).indexOf(node);
                    node.style.transitionDelay = `${index * 0.1}s`;
                }
            });
        });
    });
    
    const productContainer = document.getElementById('product-container');
    if (productContainer) {
        gridObserver.observe(productContainer, { childList: true });
    }
}
