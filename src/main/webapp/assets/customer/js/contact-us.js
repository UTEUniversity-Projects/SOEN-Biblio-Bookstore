import { toast } from "./toast.js";

const urlParams = new URLSearchParams(window.location.search);

if (urlParams.get("message") === "success") {
    toast({
        title: "Thành công!",
        message: "Yêu cầu hỗ trợ đã được gửi.",
        type: "success",
        duration: 3000,
    });
    setTimeout(() => {
        history.back();
    }, 3000);
}

if (urlParams.get("message") === "error") {
    toast({
        title: "Lỗi!",
        message: "Đã xảy ra lỗi trong quá trình gửi yêu cầu.",
        type: "error",
        duration: 3000,
    });
    setTimeout(() => {
        history.back();
    }, 3000);
}
