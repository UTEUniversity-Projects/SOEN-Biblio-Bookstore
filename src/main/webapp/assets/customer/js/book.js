import { toast } from './toast.js';
import { formatCurrencyVND } from '../../commons/js/format-currency.js';
import {zoomImage} from "../../commons/js/zoom-image.js";

export function addToCart () {
	toast({
		title: '', message: 'Đã thêm vào giỏ hàng'
	});
}

$(document).ready(() => {
	const isElementInViewport = (el) => {
		const rect = el.getBoundingClientRect();
		const windowHeight = (window.innerHeight || document.documentElement.clientHeight);
		return (
			rect.top <= (windowHeight + 120) &&
			rect.bottom >= 0 &&
			rect.left <= (window.innerWidth || document.documentElement.clientWidth) &&
			rect.right >= 0
		);
	};

	const lazyLoadElements = document.querySelectorAll('.product-content');

	const generateStars = (reviewRate) => {
		let stars = '';
		for (let i = 1; i <= 5; i++) {
			if (reviewRate >= i) {
				stars += '<i class="ri-star-fill"></i>';
			} else if (reviewRate > i - 1 && reviewRate < i) {
				stars += '<i class="ri-star-half-line"></i>';
			} else {
				stars += '<i class="ri-star-line"></i>';
			}
		}
		return stars;
	};

	const generateBook = (book) => {
		return `
							<div class="mix col-xxl-2 col-xl-3 col-4 cr-product-box mb-5">
                            <div class="cr-product-card" data-book-id="${book.id}" >
                                <div class="cr-product-image h-[auto]">
                                    <div class="image-zoom"
								         style="--url: url(${book.imageUrl.replace(/\\/g, '/')});
								            --zoom-x: 0%;
								            --zoom-y: 0%;
								            --display: none;">
                                    <img src="${contextPath}${book.imageUrl}" alt="${book.title}">
                                    <img role="presentation" alt="" style="width: 0 !important; height: 0!important;" src="${contextPath}/assets/owner/img/book/TrenDuongBang/image1.jpg" class="zoomImg" style="position: absolute; top: -2141.2px; left: -1465.35px; opacity: 0; width: 2560px; height: 2560px; border: none; max-width: none; max-height: none;"></div>
                                    <div class="cr-side-view">
                                        <a class="model-oraganic-product" data-bs-toggle="modal" href="#quickview-${book.id}" role="button">	
                                            <i class="ri-eye-line"></i>
                                        </a>
                                    </div>
                                    <a class="cr-shopping-bag add-to-cart-btn" href="javascript:void(0)" data-quantity="1">
                                        <i class="ri-shopping-bag-line"></i>
                                    </a>
                                </div>
                                <div class="cr-product-details">
                                    <div class="cr-brand">
                                        <p>${book.categoryName}</p>
                                        <div class="cr-star">
                                            ${generateStars(book.reviewRate)}
                                            <p>(${book.reviewRate})</p>
                                        </div>
                                    </div>
                                    <a href="${contextPath}/book?id=${book.id}" class="title">${book.title}</a>
                                    <p class="cr-price">
                                        ${book.salePrice === book.sellingPrice
										? `<span class="new-price price-value">${formatCurrencyVND(book.sellingPrice)}</span>`
										: `<span class="new-price price-value">${formatCurrencyVND(book.salePrice)}</span>
			   							<span class="old-price price-value">${formatCurrencyVND(book.sellingPrice)}</span>`}
                                    </p>
                                </div>
                            </div>
                        </div>
						`;
	};

	const generateModal = (book) => {
		return `
				<div class="modal fade quickview-modal" id="quickview-${book.id}" aria-hidden="true" tabindex="-1" data-book-id="${book.id}">
			        <div class="modal-dialog modal-dialog-centered cr-modal-dialog">
			            <div class="modal-content">
			                <button type="button" class="cr-close-model btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			                <div class="modal-body">
			                    <div class="row">
			                        <div class="col-md-5 col-sm-12 col-xs-12">
			                            <div class="zoom-image-hover modal-border-image" style="position: relative; overflow: hidden;">
			                                <img src="${contextPath}${book.imageUrl}" alt="product-tab-2" class="product-image">
			                            <img role="presentation" alt="" src="${contextPath}${book.imageUrl}" class="zoomImg" style="position: absolute; top: 0; left: 0; opacity: 0; width: 750px; height: 750px; border: none; max-width: none; max-height: none;"></div>
			                        </div>
			                        <div class="col-md-7 col-sm-12 col-xs-12">
			                            <div class="cr-size-and-weight-contain">
			                                <h2 class="heading">
			                                        ${book.title}
			                                </h2>
			                            </div>
			                            <div class="cr-size-and-weight">
			                                <div class="cr-review-star">
			                                    <div class="cr-star">
			                                        ${generateStars(book.reviewRate)}                                     
			                                    </div>
			                                    <p>( ${book.numberOfReviews} Reviews)</p>
			                                </div>
			                                <div class="cr-product-price">
			                                    <span class="new-price">${formatCurrencyVND(book.sellingPrice)}</span>
			                                        
			                                </div>
			                                <div class="cr-size-weight">
			                                    <h5><span>Tình trạng</span> :</h5>
			                                    <div class="cr-kg">
			                                        <ul>
			                                            <li class="active-color">${book.condition}</li>
			                                        </ul>
			                                    </div>
			                                </div>
			                                <div class="cr-add-card">
			                                    <div class="cr-qty-main">
			                                        <input type="text" value="1" minlength="1" maxlength="20" class="quantity">
			                                        <button type="button" class="plus">+</button>
			                                        <button type="button" class="minus">-</button>
			                                    </div>
			                                    <div class="cr-add-button">
			                                        <button type="button" class="cr-button cr-btn-secondary cr-shopping-bag add-to-cart-btn">
			                                            Thêm vào giỏ hàng
			                                        </button>
			                                    </div>
			                                    <div class="cr-buy-button">
			                                        <button type="button" class="cr-button">
			                                            Mua ngay
			                                        </button>
			                                    </div>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
				`;
	};

	const getBooks = async () => {
		const bookList = document.querySelector('.home-book-list');

		await $.ajax({
			url: `${contextPath}/api/customer/load-book-home`, type: 'GET', success: function (result) {
				const books = result.books;

				if (bookList) bookList.innerHTML = books?.map(generateBook).join('');

				$('.modal.fade.quickview-modal').remove();
				zoomImage('.image-zoom')
				document.querySelector('body').insertAdjacentHTML('beforeend', books.map(generateModal).join(''));
			}, error: function (xhr, error) {
				console.log(error);
			}
		});
	};


	const lazyLoad = () => {
		lazyLoadElements.forEach(element => {
			if (isElementInViewport(element)) {
				getBooks();

				window.removeEventListener('scroll', onScroll);
				window.removeEventListener('resize', onScroll);
			}
		});
	};

	const onScroll = () => {
		lazyLoad();
	};

	window.addEventListener('scroll', onScroll);
	window.addEventListener('resize', onScroll);
});
