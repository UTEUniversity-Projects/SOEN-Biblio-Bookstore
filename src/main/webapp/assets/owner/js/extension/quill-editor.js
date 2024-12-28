document.addEventListener("DOMContentLoaded", function() {

    document.getElementById('review-btn').addEventListener('click', function(event) {
        event.preventDefault();
        document.getElementById('review-content').innerHTML = editor.root.innerHTML;
        document.getElementById('review-container').style.display = 'block';
    });

    document.getElementById('save-btn').addEventListener('click', function() {
        const editorContent = editor.root.innerHTML;

        fetch('/save-content', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                content: editorContent,
            }),
        })
            .then(response => response.json())
            .then(data => {
                alert('Content saved successfully!');
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('Error saving content!');
            });
    });
});
