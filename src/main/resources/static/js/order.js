document.addEventListener("DOMContentLoaded", function() {
    const itemSelect = document.getElementById("itemSelect");
    const sizeSelect = document.getElementById("sizeSelect");

    itemSelect.addEventListener("change", function () {
        const selectedItem = itemSelect.value;

        // Reset options
        sizeSelect.innerHTML = `
            <option value="">Select Size</option>
            <option value="5">5L</option>
            <option value="10">10L</option>
            <option value="20">20L</option>
        `;

        // If Lamington → remove 5L
        if (selectedItem === "Lamingtons") {
            sizeSelect.innerHTML = `
                <option value="">Select Size</option>
                <option value="10">10L</option>
                <option value="20">20L</option>
            `;
        }
    });
});