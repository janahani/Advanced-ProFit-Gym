function validateAssignForm() {
        const coaches = document.getElementById('select-coaches');
        const classes = document.getElementById('select-classes');
        const from = document.getElementById('from');
        const to = document.getElementById('to');
        const price = document.querySelector('input[name="price"]:checked');
        const classPrice = document.getElementById('classPrice');
        const attendants = document.getElementById('attendants');
        const days = document.querySelectorAll('input[name="days[]"]:checked');
    
        const coachError = document.getElementById('coach-err');
        const classError = document.getElementById('class-err');
        const fromError = document.getElementById('from-err');
        const toError = document.getElementById('to-err');
        const isFreeError = document.getElementById('isFree-err');
        const priceError = document.getElementById('price-err');
        const attendantsError = document.getElementById('attendants-err');
        const daysError = document.getElementById('days-err');
    
        let isValid = true;
    
        // Coach validation
        if (coaches.value === '') {
            coachError.innerText = 'Coach is required';
            isValid = false;
        } else {
            coachError.innerText = '';
        }
    
        // Class validation
        if (classes.value === '') {
            classError.innerText = 'Class is required';
            isValid = false;
        } else {
            classError.innerText = '';
        }
    
        // From validation
        if (from.value.trim() === '') {
            fromError.innerText = 'From is required';
            isValid = false;
        } else {
            fromError.innerText = '';
        }
    
        // To validation
        if (to.value.trim() === '') {
            toError.innerText = 'To is required';
            isValid = false;
        } else {
            toError.innerText = '';
        }
    
        // Price validation
        if (!price) {
            isFreeError.innerText = 'Price selection is required';
            isValid = false;
        } else {
            isFreeError.innerText = '';
    
            // If price is not free, validate classPrice
            if (price.value === 'NotFree' && (classPrice.value.trim() === '' || classPrice.value <= 0)) {
                priceError.innerText = 'Invalid price';
                isValid = false;
            } else {
                priceError.innerText = '';
            }
        }
    
        // Attendants validation
        if (attendants.value.trim() === '' || attendants.value <= 0) {
            attendantsError.innerText = 'Invalid number of attendants';
            isValid = false;
        } else {
            attendantsError.innerText = '';
        }
    
        // Days validation
        if (days.length === 0) {
            daysError.innerText = 'At least one day must be selected';
            isValid = false;
        } else {
            daysError.innerText = '';
        }
    
        return isValid;
    }
    

function validateAddForm() {
    const name = document.getElementById('name');
    const descr = document.getElementById('descr');
    const image = document.getElementById('image');

    const nameError = document.getElementById('name-err');
    const descrError = document.getElementById('descr-err');
    const imageError = document.getElementById('img-err');

    let isValid = true;

    // Name validation
    if (name.value.trim() === '') {
        nameError.innerText = 'Name is required';
        isValid = false;
    } else {
        nameError.innerText = '';
    }

    // Description validation
    if (descr.value.trim() === '') {
        descrError.innerText = 'Description is required';
        isValid = false;
    } else {
        descrError.innerText = '';
    }

    // Image validation
    if (image.value.trim() === '') {
        imageError.innerText = 'Image is required';
        isValid = false;
    } else {
        imageError.innerText = '';
    }

    return isValid;
}


