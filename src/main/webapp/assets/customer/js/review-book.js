import {toast} from "./toast.js";

document.querySelectorAll(".btn-review").forEach(item => {
    item.addEventListener('click', function (event) {
        const productItem = this.closest('.product-item');
        const productId = productItem.dataset.productId;
        console.log(productId);
        const reviewModal = new bootstrap.Modal(document.getElementById('reviewModal'));
        document.getElementById('reviewModal').querySelector(".product-id").value = productId;
        reviewModal.show();
    });
});

document.getElementById("btn-submit-review").addEventListener("click", function () {
    const reviewContent = document.getElementById("exampleFormControlTextarea1").value;

    const productId = document.getElementById('reviewModal').querySelector(".product-id").value;

    const ratingElements = document.getElementsByName("rating");
    let ratingValue = null;

    for (let i = 0; i < ratingElements.length; i++) {
        if (ratingElements[i].checked) {
            ratingValue = ratingElements[i].value;
            break;
        }
    }

    const isReady = document.getElementById("is-ready").checked;

    const orderId = $("#order-id").data("order-id");

    console.log("Review Content:", reviewContent);
    console.log("Product ID:", productId);
    console.log("Rating:", ratingValue);
    console.log("Ready to recommend:", isReady);

    $.ajax({
        url: `${contextPath}/api/customer/review/add`,
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify({
            content: reviewContent,
            rate: ratingValue,
            bookTemplateId: productId,
            readyToIntroduce: isReady,
            orderId: orderId
        }),
        success: function (data) {
            toast({
                title: "Thành công", message: data.message, type: 'success', duration: 3000
            });
            console.log(data);
            // $('.product-item').each(function() {
            //     const itemProductId = $(this).data('product-id');
            //     if (String(itemProductId) === String(productId)) {
            //         $(this).find('.btn-review')
            //             .removeClass('d-flex')
            //             .addClass('d-none');
            //     } else {
            //         console.log("no");
            //     }
            // });
            $('#reviewModal').modal('hide');
            reloadStepper(orderId);
        },
        error: function (xhr, status, error) {
            toast({
                title: "Thất bại", message: data.message, type: 'error', duration: 3000
            })
        }
    });
});