/**
 * Adds a zoom effect on an image when the mouse moves over it.
 * The zoomed image appears within a specified area and moves with the cursor position.
 *
 * @param {string} selector - The ID of the image element to which the zoom effect will be applied.
 * @example
 * // To apply the zoom effect to an image with the ID 'productImage':
 * zoomImage('.productImage');
 */

export function zoomImage(selector) {
	const imageZooms = document.querySelectorAll(selector);

	imageZooms.forEach(imageZoom => {
		imageZoom?.addEventListener("mousemove", (event) => {
			imageZoom.style.setProperty("--display", "block");
			let pointer = {
				x: (event.offsetX * 100) / imageZoom.offsetWidth,
				y: (event.offsetY * 100) / imageZoom.offsetHeight,
			};
			imageZoom.style.setProperty("--zoom-x", pointer.x + "%");
			imageZoom.style.setProperty("--zoom-y", pointer.y + "%");
		});
		imageZoom?.addEventListener("mouseleave", () => {
			imageZoom.style.setProperty("--display", "none");
		});
	});
}