document.addEventListener("DOMContentLoaded", () => {
    window.editor = new Quill("#editor", {
        theme: "snow"
    }); // Export globally if needed
});
