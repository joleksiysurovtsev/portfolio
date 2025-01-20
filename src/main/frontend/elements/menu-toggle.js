// Frontend/elements/menu-toggle.js

// Function to initialize menu functionality
const initMenu = () => {
    const toggle = document.getElementById('nav-toggle');
    const nav = document.getElementById('nav-menu');

    if (toggle && nav) {
        toggle.addEventListener('click', () => {
            nav.classList.toggle('show');
            console.log('Toggle clicked, show class:', nav.classList.contains('show')); // Debug log
        });

        // Handle active link states
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', function() {
                navLinks.forEach(n => n.classList.remove('active'));
                this.classList.add('active');
                nav.classList.remove('show');
            });
        });
    }
};

// Create an observer to watch for DOM changes
const observer = new MutationObserver((mutations) => {
    mutations.forEach((mutation) => {
        if (mutation.addedNodes.length) {
            // Check if our elements exist and initialize if they do
            if (document.getElementById('nav-toggle') && document.getElementById('nav-menu')) {
                initMenu();
                observer.disconnect(); // Stop observing once we've initialized
            }
        }
    });
});

// Start observing the document with the configured parameters
observer.observe(document.body, { childList: true, subtree: true });

// Also try to initialize immediately in case the elements are already there
window.addEventListener('load', initMenu);