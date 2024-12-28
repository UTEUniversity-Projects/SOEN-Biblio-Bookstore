$(document).ready(() => {

	class Notifications {
		constructor() {
		}

		countNotifications() {
			$(".notification-count").text($(".notification-body").children().length);
		}
	}

	const notifications = new Notifications();
	notifications.countNotifications();
});
