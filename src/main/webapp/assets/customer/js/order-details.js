import {toast} from "./toast.js";

$('.btn-received').on('click', function () {
    const orderId = $('#order-id').data('order-id');
    console.log(orderId);
    const receivedModal = new bootstrap.Modal($('#receivedModal')[0]);
    receivedModal.show();
});

$('#confirmReceived').on('click', function () {
    const orderId = $('#order-id').data('order-id');
    console.log(orderId);

    $.ajax({
        url: `${contextPath}/api/staff/order/received-order`,
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify({
            orderId: orderId
        }),
        success: function (data) {
            toast({
                title: "Thành công", message: data.message, type: data.type, duration: 3000
            });
            console.log(data);
            reloadStepper(orderId);
            $('#order-status')
                .text(data.status)
                .attr('data-status', data.statusType)
                .removeClass()
                .addClass('status')
                .addClass('status--' + data.statusStyle);
            $('.btn-review')
                .removeClass('d-none')
                .addClass('d-flex');
            $('.btn-received')
                .addClass('d-none');
            $('#receivedModal').modal('hide');
        },
        error: function (xhr, status, error) {
            toast({
                title: "Thất bại", message: data.message, type: 'error', duration: 3000
            })
        }
    });
})

$('.product-title').on('click', function () {
    window.location.href = $(this).data('href');
});