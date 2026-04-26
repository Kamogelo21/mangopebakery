document.addEventListener("DOMContentLoaded", function () {

    const itemSelect = document.getElementById("item");
    const sizeSelect = document.getElementById("size");
    const qtyInput = document.getElementById("qty");
    const orderList = document.getElementById("orderList");

    let cart = [];

    // =========================
    // 🔄 HANDLE ITEM CHANGE (LAMINGTON FIX)
    // =========================
    itemSelect.addEventListener("change", function () {
        const selectedItem = itemSelect.value;

        // Reset sizes
        sizeSelect.innerHTML = `
            <option value="">Size</option>
            <option value="5">5L</option>
            <option value="10">10L</option>
            <option value="20">20L</option>
        `;

        // Remove 5L if Lamingtons
        if (selectedItem === "Lamingtons") {
            sizeSelect.innerHTML = `
                <option value="">Size</option>
                <option value="10">10L</option>
                <option value="20">20L</option>
            `;
        }
    });

    // =========================
    // ➕ ADD ITEM TO CART
    // =========================
    window.addItem = function () {
        const item = itemSelect.value;
        const size = sizeSelect.value;
        const qty = parseInt(qtyInput.value);

        if (!item || !size || !qty || qty <= 0) {
            alert("Select item, size and quantity");
            return;
        }

        const entry = {
            item: item,
            size: size,
            qty: qty
        };

        cart.push(entry);

        renderCart();

        // Reset inputs (nice UX)
        sizeSelect.value = "";
        qtyInput.value = 1;
    };

    // =========================
    // 🧾 RENDER CART
    // =========================
    function renderCart() {
        orderList.innerHTML = "";

        cart.forEach((entry, index) => {
            const li = document.createElement("li");

            li.innerHTML = `
                ${entry.item} ${entry.size}L x${entry.qty}
                <button onclick="removeItem(${index})">❌</button>
            `;

            orderList.appendChild(li);
        });
    }

    // =========================
    // ❌ REMOVE ITEM
    // =========================
    window.removeItem = function (index) {
        cart.splice(index, 1);
        renderCart();
    };

    // =========================
    // 🚀 PREPARE ORDER FOR SUBMIT
    // =========================
    window.prepareOrder = function () {
        const hiddenInput = document.getElementById("orderDetails");

        const formatted = cart.map(entry =>
            `${entry.item} ${entry.size}L x${entry.qty}`
        ).join("\n");

        hiddenInput.value = formatted;
    };

});