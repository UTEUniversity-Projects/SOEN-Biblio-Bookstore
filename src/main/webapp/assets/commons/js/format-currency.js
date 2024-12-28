export function formatCurrencyVND (value) {
	return new Intl.NumberFormat('vi-VN', {
		style: 'currency',
		currency: 'VND',
		minimumFractionDigits: 0,
		maximumFractionDigits: 0
	}).format(value);
}

[...document.querySelectorAll('.price-value')].forEach(el => {
	console.log(el);
	const formattedValue = formatCurrencyVND(el.textContent.trim());
	if (el.classList.contains('minus-value')) {
		el.innerHTML = `-${formattedValue}`;
	} else {
		el.innerHTML = `${formattedValue}`;
	}

	function formatCurrencyVND (value) {
		return new Intl.NumberFormat('vi-VN', {
			style: 'currency',
			currency: 'VND',
			minimumFractionDigits: 0,
			maximumFractionDigits: 0
		}).format(value);
	}
});