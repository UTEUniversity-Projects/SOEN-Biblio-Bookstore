[...document.querySelectorAll('.discount-percent')].forEach(el => {
    const discountValue = parseFloat(el.textContent.trim());

    const roundedDiscount = (discountValue % 1 === 0) ? discountValue.toFixed(0) : discountValue.toFixed(2);

    el.innerHTML = `-${roundedDiscount}%`;
});