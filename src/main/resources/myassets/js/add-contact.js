document.getElementById('contactForm').addEventListener('submit', function(event) {
    event.preventDefault(); // отменяем обычную отправку формы

    // Получаем элементы формы и блоки ошибок
    const lastNameInput = document.getElementById('lastName');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const lastNameError = document.getElementById('lastNameError');
    const phoneNumberError = document.getElementById('phoneNumberError');

    // Очищаем предыдущие ошибки
    lastNameError.textContent = '';
    phoneNumberError.textContent = '';

    const lastName = lastNameInput.value.trim();
    const phoneNumber = phoneNumberInput.value.trim();

    let hasError = false;

    if (!lastName) {
        lastNameError.textContent = 'Last Name is required';
        hasError = true;
    }

    if (!phoneNumber) {
        phoneNumberError.textContent = 'Phone Number is required';
        hasError = true;
    } else {
        const phoneRegex = /^\+\d+$/
        ;
        if (!phoneRegex.test(phoneNumber)) {
            phoneNumberError.textContent = 'Phone Number should contain only digits and starts with +';
            hasError = true;
        }
    }

    if (hasError) {
        return; // Прекращаем отправку формы, если есть ошибки
    }

    // Если валидация прошла — отправляем запрос на сервер
    fetch('/api/contacts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({ lastName, phoneNumber })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Contact saved:', data);
            window.location.href = '/contacts'; // редирект после успешного сохранения
        })
        .catch(error => {
            // Покажем ошибку сервера под формой (например, под последним полем)
            phoneNumberError.textContent = 'Error saving contact: ' + error.message;
        });
});
