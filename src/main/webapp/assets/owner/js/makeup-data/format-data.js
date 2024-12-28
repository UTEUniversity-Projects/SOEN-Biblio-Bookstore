function truncateTextWithTooltip(selector) {
    const elements = document.querySelectorAll(selector);

    elements.forEach(el => {
        const maxLength = el.dataset.maxLength ? parseInt(el.dataset.maxLength, 10) : 150;
        const originalText = el.textContent;

        if (originalText.length > maxLength) {
            el.textContent = originalText.slice(0, maxLength) + '...';
        }
    });
}
truncateTextWithTooltip('.row-introduction');