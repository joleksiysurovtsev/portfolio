window.toggleTheme = function () {
    const root = document.documentElement;
    const currentTheme = root.getAttribute('theme') || 'light';
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    root.setAttribute('theme', newTheme);
    localStorage.setItem('theme', newTheme);
    return newTheme;
};