$(function() {
    $('input[name="birthday"]').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true,
      minYear: 1901,
      maxYear: parseInt(moment().format('YYYY'), 10)
    }, function(start, end, label) {
      var years = moment().diff(start, 'years');
    });
});
document.getElementById('edit-button')?.addEventListener('click', function(event) {
    event.preventDefault(); // Ngăn điều hướng trang

    const isEditing = this.textContent.trim() === 'Chỉnh sửa';

    if (isEditing) {
        // Bật các trường input để chỉnh sửa
        document.getElementById('name-customer-edit').disabled = false;
        document.getElementById('email-customer-edit').disabled = false;
        document.getElementById('phone-customer-edit').disabled = false;
        document.getElementById('birthday-customer-edit').disabled = false;
        document.getElementById('address-customer-edit').disabled = false;
        document.getElementById('username-customer-edit').disabled = false;
        document.getElementById('password-customer-edit').disabled = false;
        document.getElementById('count-order-customer-edit').disabled = false;
        document.getElementById('cost-customer-edit').disabled = false;

        document.getElementById('select-gender').disabled = false;
        document.getElementById('select-nation').disabled = false;
        document.getElementById('select-province').disabled = false;
        document.getElementById('select-district').disabled = false;
        document.getElementById('select-villiage').disabled = false;

        // Hiển thị icon chỉnh sửa
        document.getElementById('edit-avatar').style.display = 'block';

        // Đổi nút thành "Lưu"
        this.textContent = 'Lưu';
    } else {
        // Khóa các trường sau khi lưu
        document.getElementById('name-customer-edit').disabled = true;
        document.getElementById('email-customer-edit').disabled = true;
        document.getElementById('phone-customer-edit').disabled = true;
        document.getElementById('birthday-customer-edit').disabled = true;
        document.getElementById('address-customer-edit').disabled = true;
        document.getElementById('username-customer-edit').disabled = true;
        document.getElementById('password-customer-edit').disabled = true;
        document.getElementById('count-order-customer-edit').disabled = true;
        document.getElementById('cost-customer-edit').disabled = true;

        document.getElementById('select-gender').disabled = true;
        document.getElementById('select-nation').disabled = true;
        document.getElementById('select-province').disabled = true;
        document.getElementById('select-district').disabled = true;
        document.getElementById('select-villiage').disabled = true;

        // Ẩn icon chỉnh sửa
        document.getElementById('edit-avatar').style.display = 'none';

        // Đổi nút lại thành "Chỉnh sửa"
        this.textContent = 'Chỉnh sửa';
    }
});

// Khi nhấn vào icon, mở hộp thoại chọn tệp
document.getElementById('edit-avatar')?.addEventListener('click', function() {
    document.getElementById('avatarUpload').click();
});

// Khi chọn tệp, đọc và hiển thị ảnh mới
document.getElementById('avatarUpload')?.addEventListener('change', function() {
    readURL(this);
});

// Hàm đọc và hiển thị ảnh mới
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('avatar-customer-edit').style.backgroundImage = `url(${e.target.result})`;
        }
        reader.readAsDataURL(input.files[0]);
    }
};
