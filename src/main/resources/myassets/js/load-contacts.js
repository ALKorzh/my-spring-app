document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/contacts')
        .then(response => response.json())
        .then(contacts => {
            const tbody = document.getElementById('contactsBody');
            tbody.innerHTML = ''; // очистить таблицу

            contacts.forEach(contact => {
                const tr = document.createElement('tr');

                const tdId = document.createElement('td');
                tdId.textContent = contact.id;
                tr.appendChild(tdId);

                const tdLastName = document.createElement('td');
                tdLastName.textContent = contact.lastName;
                tr.appendChild(tdLastName);

                const tdPhone = document.createElement('td');
                tdPhone.textContent = contact.phoneNumber;
                tr.appendChild(tdPhone);

                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error('Error loading contacts:', err);
        });
});
