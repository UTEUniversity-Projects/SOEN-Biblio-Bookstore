import { APP_SCRIPT_URL } from './ggDriveFolderId.js';

/**
 * Uploads an image to the server.
 *
 * @param {string} dir - The directory where the image should be uploaded.
 * @param {string} inputSelector - The selector for the file input element.
 * @param {string} [fileName=''] - An optional file name to be used on the server.
 * @returns {Promise<null|*>} - The server response if the upload is successful, or null if there was an error or no files were selected.
 */
export async function uploadImage (dir, inputSelector, fileName = '') {
	try {
		const fileInput = document.querySelector(inputSelector);
		let files = fileInput?.files;
		if (!files || files.length === 0) {
			return null;
		}

		const formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}
		formData.append('dir', dir);
		formData.append('fileName', fileName);

		return await $.ajax({
			url: `${contextPath}/upload`,
			type: 'POST',
			data: formData,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false
		});

	} catch (error) {
		console.error('Error uploading file:', error);
		return null;
	}
}

export async function readImages(fileSelector) {
	const postData = [];
	const file = document.getElementById(fileSelector).files;
	const fileList = [...file];

	// Tạo mảng các Promise cho mỗi file
	const filePromises = fileList.map((file, index) => {
		return new Promise((resolve, reject) => {
			const reader = new FileReader();
			reader.readAsDataURL(file);

			// Khi đọc xong file, thêm vào postData
			reader.onload = () => {
				const data = reader.result.split(",")[1]; // Lấy phần base64 của file
				postData[index] = {
					name: file.name,
					type: file.type,
					data: data,
				};
				resolve(); // Resolve khi file được xử lý xong
			};

			// Nếu có lỗi khi đọc file, reject Promise
			reader.onerror = (error) => reject(`Lỗi khi đọc file: ${file.name}`);
		});
	});

	// Chờ đợi tất cả file được xử lý xong
	await Promise.all(filePromises); // Chờ tất cả các Promise hoàn thành
	return postData; // Trả về postData sau khi tất cả các file đã được xử lý
}

export async function uploadImageToDrive(postData) {
	try {
		const response = await fetch(
			APP_SCRIPT_URL,
			{
				method: "POST",
				body: JSON.stringify(postData),
			}
		);
		return await response.json();
	} catch (error) {
		alert("Vui lòng thử lại");
	}
}
