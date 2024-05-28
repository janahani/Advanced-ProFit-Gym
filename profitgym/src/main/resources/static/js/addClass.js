document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('.add-class'); // Select the form by class
    if (form) {
        form.addEventListener('submit', function(event) {
            const isValid = validateForm();
            if (!isValid) {
                event.preventDefault(); // Prevent form submission if validation fails
            }
        });
    }

    function validateForm() {
        var nameInput = document.querySelector('input[name="Name"]');
        var descriptionInput = document.querySelector('textarea[name="Description"]');
        var fileInput = document.querySelector('input[name="file"]');
        var daysCheckboxes = document.querySelectorAll('input[name="week-days[]"]:checked');

        var isValid = true;

        // Validate class name
        if (!nameInput.value.trim()) {
            document.getElementById("name-error").textContent = "Please enter a class name.";
            isValid = false;
        } else {
            document.getElementById("name-error").textContent = "";
        }

        // Validate description
        if (!descriptionInput.value.trim()) {
            document.getElementById("description-error").textContent = "Please enter a description.";
            isValid = false;
        } else {
            document.getElementById("description-error").textContent = "";
        }

        // Validate file upload
        if (!fileInput.value) {
            document.getElementById("file-error").textContent = "Please select an image file.";
            isValid = false;
        } else {
            document.getElementById("file-error").textContent = "";
        }

        // Validate days checkboxes
        if (daysCheckboxes.length === 0) {
            document.getElementById("days-error").textContent = "Please select at least one day.";
            isValid = false;
        } else {
            document.getElementById("days-error").textContent = "";
        }

        return isValid;
    }
});
