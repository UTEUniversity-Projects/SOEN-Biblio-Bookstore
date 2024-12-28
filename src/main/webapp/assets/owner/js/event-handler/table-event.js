document.addEventListener("DOMContentLoaded", () => {
  const tableRows = document.querySelectorAll(".responsive-table tr");

  tableRows.forEach(row => {
    row.addEventListener("mouseenter", () => {
      row.style.transform = "translateY(-5px)";
      row.style.borderRadius = "10px";
      row.style.zIndex = "1";

      const firstCell = row.querySelector("td:first-child");
      const lastCell = row.querySelector("td:last-child");

      if (firstCell) {
        firstCell.style.borderTopLeftRadius = "10px";
        firstCell.style.borderBottomLeftRadius = "10px";
      }
      if (lastCell) {
        lastCell.style.borderTopRightRadius = "10px";
        lastCell.style.borderBottomRightRadius = "10px";
      }
    });

    row.addEventListener("mouseleave", () => {
      row.style.transform = "translateY(0)";
      row.style.borderRadius = "0";
      row.style.zIndex = "0";

      const firstCell = row.querySelector("td:first-child");
      const lastCell = row.querySelector("td:last-child");

      if (firstCell) {
        firstCell.style.borderTopLeftRadius = "0";
        firstCell.style.borderBottomLeftRadius = "0";
      }
      if (lastCell) {
        lastCell.style.borderTopRightRadius = "0";
        lastCell.style.borderBottomRightRadius = "0";
      }
    });
  });
});

document.addEventListener("DOMContentLoaded", function() {
  const dropdownItems = document.querySelectorAll('.dropdown-item');

  dropdownItems.forEach(item => {
    item.addEventListener('mouseover', function() {
      const value = item.getAttribute('data-value');

      switch(value) {
        case 'view':
          item.style.backgroundColor = '#f0f0f0';
          item.style.color = '#000';
          item.style.fontWeight = 'bold';
          break;
        case 'update':
          item.style.backgroundColor = '#e0f7fa';
          item.style.color = '#006064';
          item.style.fontWeight = 'bold';
          break;
        case 'delete':
          item.style.backgroundColor = '#ffebee';
          item.style.color = '#d32f2f';
          item.style.fontWeight = 'bold';
          break;
        default:
          break;
      }
    });

    item.addEventListener('mouseout', function() {
      item.style.backgroundColor = '';
      item.style.color = '';
      item.style.fontWeight = '';
    });
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const dropdownToggles = document.querySelectorAll('.btn.dropdown-toggle');
  const dropdownMenus = document.querySelectorAll('.dropdown-menu');

  dropdownToggles.forEach(toggle => {
    const menu = toggle.nextElementSibling;

    toggle.addEventListener('click', (event) => {
      event.stopPropagation();
      closeAllDropdowns();
      menu.classList.toggle('show');
    });

    menu.addEventListener('mouseleave', () => {
      setTimeout(() => {
        if (!menu.matches(':hover')) {
          menu.classList.remove('show');
        }
      }, 0);
    });
  });

  document.addEventListener('click', () => {
    closeAllDropdowns();
  });

  function closeAllDropdowns() {
    dropdownMenus.forEach(menu => menu.classList.remove('show'));
  }
});

