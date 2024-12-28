function updateStepper(data) {
    const steps = document.querySelectorAll('.stepper__step');
    const stepStatuses = data.steps;
    const foreground = document.querySelector('.stepper__line-foreground');

    steps.forEach(step => {
        step.classList.remove('stepper__step--finish', 'stepper__step--pending');
        const iconElement = step.querySelector('.stepper__step-icon');
        if (iconElement) {
            iconElement.classList.remove('stepper__step-icon--finish', 'stepper__step-icon--pending');
        }
        const dateElement = step.querySelector('.stepper__step-date');
        if (dateElement) {
            dateElement.textContent = '';
        }
    });

    stepStatuses.forEach((stepData, index) => {
        const step = steps[index];

        if (step) {
            step.classList.add('stepper__step--finish');
            const iconElement = step.querySelector('.stepper__step-icon');
            if (iconElement) {
                iconElement.classList.add('stepper__step-icon--finish');
            }

            const dateElement = step.querySelector('.stepper__step-date');
            if (dateElement) {
                dateElement.textContent = stepData.date;
            }
        }
    });

    const length = stepStatuses.length;
    const step = steps[length];
    if (step) {
        step.classList.add('stepper__step--pending');
        const iconElement = step.querySelector('.stepper__step-icon');
        if (iconElement) {
            iconElement.classList.add('stepper__step-icon--pending');
        }
        const dateElement = step.querySelector('.stepper__step-date');
        if (dateElement) {
            dateElement.textContent = '';
        }
    }

    if (foreground && length > 0) {
        const multiplier = Math.min(4, length);
        foreground.style.width = `calc((60px + 115px) * ${multiplier})`;
    }
}

function reloadStepper(orderId) {
    $.ajax({
        url: `${contextPath}/api/staff/order/get-order-history?orderId=${orderId}`,
        method: 'GET',
        success: function(data) {
            updateStepper(data);
        },
        error: function(error) {
            console.error('Lỗi khi tải dữ liệu:', error);
        }
    });
}

$(document).ready(function() {
    const orderId = $("#order-id").data("order-id");
    console.log(orderId);
    reloadStepper(orderId);
});