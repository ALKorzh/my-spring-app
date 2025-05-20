document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('signupForm');

    if (form) {
        form.addEventListener('submit', (e) => {
            clearErrors();
            let valid = true;

            const username = form.username.value.trim();
            if (username.length < 4 || username.length > 40) {
                showError('usernameError', 'Username must be between 4 and 40 characters');
                valid = false;
            }

            const email = form.email.value.trim();
            if (!validateEmail(email)) {
                showError('emailError', 'Invalid email format');
                valid = false;
            }

            const password = form.password.value;
            if (password.length < 6) {
                showError('passwordError', 'Password must be at least 6 characters');
                valid = false;
            }

            const confirmPassword = form.confirmPassword.value;
            if (password !== confirmPassword) {
                showError('confirmPasswordError', 'Passwords do not match');
                valid = false;
            }

            if (!valid) {
                e.preventDefault();
            }
        });
    }

    function showError(id, message) {
        const el = document.getElementById(id);
        if (el) {
            el.textContent = message;
        }
    }

    function clearErrors() {
        ['usernameError', 'emailError', 'passwordError', 'confirmPasswordError'].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.textContent = '';
        });
    }

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email.toLowerCase());
    }
});
