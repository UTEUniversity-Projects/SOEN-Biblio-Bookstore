import { toast } from './toast.js';
import { debounce } from '../../commons/js/debounce.js';
import { zoomImage } from '../../commons/js/zoom-image.js';
import { formatCurrencyVND } from '../../commons/js/format-currency.js';

$(document).ready(() => {

	class SearchBook {
		constructor () {
		}

		async search (searchData) {

			console.log(searchData);

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
							<div class="mix col-xxl-3 col-xl-4 col-6 cr-product-box mb-5">
                            <div class="cr-product-card" data-book-id="${book.id}">
                                <div class="cr-product-image">
                                    <div class="cr-image-inner image-zoom"
								         style="--url: url(${book.imageUrl.replace(/\\/g, '/')});
								            --zoom-x: 0%;
								            --zoom-y: 0%;
								            --display: none;">
                                    <img src="${book.imageUrl}" alt="${book.title}">
                                    <img role="presentation" alt="" src="http://localhost:8080/assets/owner/img/book/TrenDuongBang/image1.jpg" class="zoomImg" style="position: absolute; top: -2141.2px; left: -1465.35px; opacity: 0; width: 2560px; height: 2560px; border: none; max-width: none; max-height: none;"></div>
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
                                        <span class="new-price price-value">${formatCurrencyVND(book.sellingPrice)}</span>
                                        <span class="old-price price-value">${formatCurrencyVND(book.sellingPrice)}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
						`;
			};

			const generateModal = (book) => {
				return `
				<div class="modal fade quickview-modal" id="quickview-${book.id}" aria-hidden="true" tabindex="-1">
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
			                                        <input type="text" placeholder="." value="1" minlength="1" maxlength="20" class="quantity">
			                                        <button type="button" class="plus">+</button>
			                                        <button type="button" class="minus">-</button>
			                                    </div>
			                                    <div class="cr-add-button">
			                                        <button type="button" class="cr-button cr-btn-secondary cr-shopping-bag">
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

			const generateCategory = (category) => {
				return `<div class="checkbox-group gap-x-2">
                                    <input type="checkbox" id="${category.id}" value="${category.id}" ${getUrlParam("categoryId") === category.id.toString() ? "checked" : ""} class="category-item">
                                    <label for="${category.id}" class="pr-2">${category.categoryName}</label>
                                    <span>(${category.bookCount})</span>
                                </div>`;
			};

			$('.book-list').html('                    <div class="loading">\n' + '                        <div class="mx-auto w-[30px] h-[30px] rounded-full border-[4px] border-solid border-green-400 border-t-transparent animate-spin"></div>\n' + '                    </div>');
			$('.cr-pagination').hide();

			const handleSuccess = (result) => {

				const bookList = document.querySelector('.book-list');
				const categoryList = document.querySelector('.category');
				const { books, quantity, category } = result;

				if (categoryList) {
					if (isCategoryClicked === false) {
						categoryList.innerHTML = `<div class="checkbox-group gap-x-2">
                                <input type="checkbox" id="all-categories" class="category-item" ${getUrlParam("categoryId") === null ? "checked" : ""}/>
                                <label for="all-categories">Tất cả</label>
                                <span>(${quantity})</span>
                            </div>` + category?.map(generateCategory).join('');
						isCategoryClicked = false;
					}
				}

				if (books.length === 0) {
					bookList.innerHTML = `<p class="text-xl text-[#269a37] text-center">Không tìm thấy sản phẩm.</p>`;
					$('.cr-pagination').hide();
				} else {
					if (bookList) bookList.innerHTML = books?.map(generateBook).join('');

					$('.modal.fade.quickview-modal').remove();
					document.querySelector('body').insertAdjacentHTML('beforeend', books.map(generateModal).join(''));

					$('.cr-pagination').show();
					zoomImage('.image-zoom');
					generatePagination(document.querySelector('.pagination'), Math.floor(quantity / searchData.perPage) + (quantity % searchData.perPage !== 0 ? 1 : 0), parseInt(getUrlParam('page')));
				}

				$('.search-result-label').text(` ${searchData.title?.trim()} (${result.quantity} kết quả)`);

			};

			await $.ajax({

				url: `${contextPath}/api/customer/search-book`,
				type: 'POST',
				data: JSON.stringify(searchData),
				contentType: 'application/json',
				success: async function (result) {
					console.log({ result });
					handleSuccess(result);
				},
				error: function (xhr, error) {
					console.log(error);
				},
				complete: function () {
					$('.loading').addClass('hidden');
					$('.book-list').removeClass('hidden');
				}
			});

		}
	}

	const searchBook = new SearchBook();
	const searchInput = $('.search-input');
	const urlParams = new URLSearchParams(window.location.search);
	var isCategoryClicked = false;

	const searchData = {
		title: urlParams.get('title') || '',
		categoryId: urlParams.get('categoryId') || null,
		pageNumber: urlParams.get('page') || 1,
		sortBy: null,
		minPrice: 0,
		maxPrice: 10000000000000000,
		perPage: getItemQuantityPerPage(),
		condition: null,
		format: null,
		reviewRate: null
	};

	$('.btn-search').click(function (event) {
		if (!window.location.pathname.includes('/search')) return;
		event.preventDefault();

		if (searchInput.val() === '') {
			toast({
				title: 'Tìm kiếm', message: 'Vui lòng nhập thông tin tìm kiếm!'
			});
		} else {
			if (window.location.pathname.includes('/search')) {
				searchBook.search(searchData);
			}
		}
	});

	if (window.location.pathname.includes('/search')) {
		const urlParams = new URLSearchParams(window.location.search);

		if (urlParams.get('page') == null) {
			updateUrlParam('page', 1);
		}

		searchInput.on('input', debounce(function () {
			const titleValue = searchInput.val().toLowerCase();

			updateUrlParam('title', titleValue);
			updateObjectValue(searchData, 'title', titleValue);

			if (titleValue.trim() === '') {
				const urlParams = new URLSearchParams(window.location.search);
				const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
				window.history.pushState({ path: newUrl }, '', newUrl);
			}
			searchBook.search(searchData);

		}, 500));
	}

	$(document).on('click', '.category-item', function () {
		$('.category-item').each(function () {
			$(this)[0].checked = false;
			$(this).parent().removeClass('select-none pointer-events-none');
		});
		$(this)[0].checked = true;
		$(this).parent().addClass('select-none pointer-events-none');
		const categoryId = $(this).val() !== 'on' ? $(this).val() : null;
		updateUrlParam('categoryId', categoryId);
		updateUrlParam('page', 1);
		updateObjectValue(searchData, 'categoryId', categoryId);
		isCategoryClicked = true;
		if (categoryId) {
			updateUrlParam('categoryId', categoryId);
		} else {
			const urlParams = new URLSearchParams(window.location.search);
			urlParams.delete('categoryId');
			const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
			window.history.pushState({ path: newUrl }, '', newUrl);
		}
		searchBook.search(searchData);
	});

	$('.condition-item').click(function () {
		$('.condition-item').each(function () {
			$(this)[0].checked = false;
			$(this).parent().removeClass('select-none pointer-events-none');
		});
		$(this)[0].checked = true;
		$(this).parent().addClass('select-none pointer-events-none');

		const condition = $(this).val() !== 'on' ? $(this).val() : null;
		updateUrlParam('condition', condition);
		updateUrlParam('page', 1);
		updateObjectValue(searchData, 'condition', condition);

		if (condition) {
			updateUrlParam('condition', condition);
		} else {
			const urlParams = new URLSearchParams(window.location.search);
			urlParams.delete('condition');
			const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
			window.history.pushState({ path: newUrl }, '', newUrl);
		}

		searchBook.search(searchData);

	});

	$('.format-item').click(function () {
		$('.format-item').each(function () {
			$(this)[0].checked = false;
			$(this).parent().removeClass('select-none pointer-events-none');
		});
		$(this)[0].checked = true;
		$(this).parent().addClass('select-none pointer-events-none');
		updateObjectValue(searchData, 'format', $(this).val() == 'on' ? null : $(this).val());

		const format = $(this).val() !== 'on' ? $(this).val() : null;
		updateUrlParam('format', format);
		updateUrlParam('page', 1);
		updateObjectValue(searchData, 'format', format);

		if (format) {
			updateUrlParam('format', format);
		} else {
			const urlParams = new URLSearchParams(window.location.search);
			urlParams.delete('format');
			const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
			window.history.pushState({ path: newUrl }, '', newUrl);
		}

		searchBook.search(searchData);

	});

	$('.review-item').click(function () {
		$('.review-item').each(function () {
			$(this)[0].checked = false;
			$(this).parent().removeClass('select-none pointer-events-none');
		});
		$(this)[0].checked = true;
		$(this).parent().addClass('select-none pointer-events-none');

		updateObjectValue(searchData, 'reviewRate', $(this).val() == 'on' ? null : $(this).val());

		const reviewRate = $(this).val() !== 'on' ? $(this).val() : null;
		updateUrlParam('reviewRate', reviewRate);
		updateUrlParam('page', 1);
		updateObjectValue(searchData, 'reviewRate', reviewRate);

		if (reviewRate) {
			updateUrlParam('reviewRate', reviewRate);
		} else {
			const urlParams = new URLSearchParams(window.location.search);
			urlParams.delete('reviewRate');
			const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
			window.history.pushState({ path: newUrl }, '', newUrl);
		}

		searchBook.search(searchData);

	});

	$('.sort-by').change(function () {
		updateObjectValue(searchData, 'sortBy', $(this).val());
		searchBook.search(searchData);
	});

	document.querySelectorAll('.range-slider input[type=\'range\']').forEach(input => {
		onInput(document.querySelector('.range-slider'));
		input.oninput = debounce((e) => onInput(document.querySelector('.range-slider'), e), 500);
	});

	setValueFromParameter();

	function setValueFromParameter () {
		if (!window.location.href.includes('/search')) return;

		if (getUrlParam('title')) {
			searchInput.val(getUrlParam('title'));
		}

		if (getUrlParam('categoryId')) {
			$('.category-item').each(function () {
				$(this)[0].checked = $(this).val() === getUrlParam('categoryId');
			});
		}

		if (getUrlParam('condition')) {
			$('.condition-item').each(function () {
				$(this)[0].checked = $(this).val() === getUrlParam('condition');
			});
		}

		if (getUrlParam('format')) {
			$('.format-item').each(function () {
				$(this)[0].checked = $(this).val() === getUrlParam('format');
			});
		}

		if (getUrlParam('reviewRate')) {
			$('.review-item').each(function () {
				$(this)[0].checked = $(this).val() === getUrlParam('reviewRate');
			});
		}

		searchBook.search(searchData);

	}

	function getUrlParam (param) {
		const urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(param);
	}

	function updateUrlParam (param, value) {
		const urlParams = new URLSearchParams(window.location.search);
		urlParams.set(param, value);
		const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
		window.history.pushState({ path: newUrl }, '', newUrl);
	}

	function updateObjectValue (obj, key, value) {
		if (obj && typeof obj === 'object' && key) {
			obj[key] = value;
		}
	}

	function generatePagination (element, totalPages, page) {

		if (element) {
			while (element.firstChild) {
				element.removeChild(element.firstChild);
			}
		}

		let liTag = '';
		let active;
		let beforePage = page - 2;
		let afterPage = page + 2;

		// Previous button
		if (page > 1) {
			liTag += `<li class="btn prev"><span><i class="fas fa-angle-left"></i></span></li>`;
		}

		// First page and dots before the current range
		if (page > 3) {
			liTag += `<li class="first numb"><span>1</span></li>`;
			if (page > 4) {
				liTag += `<li class="dots"><span>...</span></li>`;
			}
		}

		// Page number buttons
		for (let plength = Math.max(1, beforePage); plength <= Math.min(totalPages, afterPage); plength++) {
			active = (page === plength) ? 'active select-none pointer-events-none' : '';
			liTag += `<li class="numb ${active}" data-page="${plength}"><span>${plength}</span></li>`;
		}

		// Dots and last page after the current range
		if (page < totalPages - 2) {
			if (page < totalPages - 3) {
				liTag += `<li class="dots"><span>...</span></li>`;
			}
			liTag += `<li class="last numb" data-page="${totalPages}"><span>${totalPages}</span></li>`;
		}

		// Next button
		if (page < totalPages) {
			liTag += `<li class="btn next"><span><i class="fas fa-angle-right"></i></span></li>`;
		}

		if (element) element.innerHTML = liTag;

		attachPaginationEvents(element, totalPages, page);

		return liTag;
	}

	function attachPaginationEvents (element, totalPages, currentPage) {
		const allButtons = element?.querySelectorAll('.pagination li') || [];
		allButtons.forEach((btn) => {
			if (btn.classList.contains('prev')) {
				btn.addEventListener('click', () => updatePageInUrl(currentPage - 1, totalPages));
			} else if (btn.classList.contains('next')) {
				btn.addEventListener('click', () => updatePageInUrl(currentPage + 1, totalPages));
			} else if (btn.classList.contains('first')) {
				btn.addEventListener('click', () => updatePageInUrl(1, totalPages));
			} else if (btn.classList.contains('last')) {
				btn.addEventListener('click', () => updatePageInUrl(totalPages, totalPages));
			} else if (btn.classList.contains('numb')) {
				const pageNumber = parseInt(btn.textContent, 10);
				btn.addEventListener('click', () => updatePageInUrl(pageNumber, totalPages));
			}
		});
	}

	function updatePageInUrl (page, totalPages) {
		scrollToTop();
		const urlParams = new URLSearchParams(window.location.search);
		urlParams.set('page', page);
		updateObjectValue(searchData, 'pageNumber', page);
		const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
		window.history.pushState({ path: newUrl }, '', newUrl);

		const paginationElement = document.querySelector('.pagination');
		generatePagination(paginationElement, totalPages, page);
		searchBook.search(searchData);
	}

	function scrollToTop () {
		window.scrollTo({
			top: 0, behavior: 'smooth'
		});
	}

	function getItemQuantityPerPage () {

		const width = window.innerWidth;

		if (width < 1400) {
			return 12;
		} else {
			return 16;
		}
	}

	function onInput (parent, e) {
		const slides = parent.querySelectorAll('input');
		const min = parseFloat(slides[0].min);
		const max = parseFloat(slides[0].max);

		let slide1 = parseFloat(slides[0].value);
		let slide2 = parseFloat(slides[1].value);

		const percentageMin = (slide1 / (max - min)) * 100;
		const percentageMax = (slide2 / (max - min)) * 100;

		parent.style.setProperty('--range-slider-value-low', percentageMin);
		parent.style.setProperty('--range-slider-value-high', percentageMax);

		if (slide1 > slide2) {
			const tmp = slide2;
			slide2 = slide1;
			slide1 = tmp;

			if (e?.currentTarget === slides[0]) {
				slides[0].insertAdjacentElement('beforebegin', slides[1]);
			} else {
				slides[1].insertAdjacentElement('afterend', slides[0]);
			}
		}

		updateObjectValue(searchData, 'minPrice', slide1);
		updateObjectValue(searchData, 'maxPrice', slide2);

		searchBook.search(searchData);

		parent.querySelector('.range-slider__display').setAttribute('data-low', slide1);
		parent.querySelector('.range-slider__display').setAttribute('data-high', slide2);
		parent.querySelector('.min-price').innerText = slide1;
		parent.querySelector('.max-price').innerText = slide2;
	}

});


