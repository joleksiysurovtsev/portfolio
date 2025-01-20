// window.Vaadin.techtField = window.Vaadin.techtField || {
//     initTechCard: function(element) {
//         if (!element.$connector) {
//             element.$connector = {
//                 updateMousePosition: function(e) {
//                     const rect = element.getBoundingClientRect();
//                     const x = e.clientX - rect.left;
//                     const y = e.clientY - rect.top;
//
//                     // Set the values in pixels
//                     element.style.setProperty("--mouse-x", `${x}px`);
//                     element.style.setProperty("--mouse-y", `${y}px`);
//
//                     // Calculate  for pointerx/y
//                     const percentX = (x / rect.width) * 100;
//                     const percentY = (y / rect.height) * 100;
//
//                     element.style.setProperty("--pointerx", `${percentX}%`);
//                     element.style.setProperty("--pointery", `${percentY}%`);
//                 }
//             };
//
//             // Add event handler
//             element.addEventListener('mousemove', element.$connector.updateMousePosition);
//             element.addEventListener('mouseenter', element.$connector.updateMousePosition);
//
//             // Set initial values
//             element.style.setProperty("--mouse-x", "100%");
//             element.style.setProperty("--mouse-y", "100%");
//             element.style.setProperty("--pointerx", "100%");
//             element.style.setProperty("--pointery", "100%");
//
//             console.log('Tech card initialized:', element);
//         }
//     }
// };

window.Vaadin.techtField = window.Vaadin.techtField || {
    initTechCard: function(element) {
        if (!element.$connector) {
            element.$connector = {
                updateMousePosition: function(e) {
                    const rect = element.getBoundingClientRect();
                    const x = e.clientX - rect.left;
                    const y = e.clientY - rect.top;

                    element.style.setProperty("--mouse-x", `${x}px`);
                    element.style.setProperty("--mouse-y", `${y}px`);

                    const percentX = (x / rect.width) * 100;
                    const percentY = (y / rect.height) * 100;

                    element.style.setProperty("--pointerx", `${percentX}%`);
                    element.style.setProperty("--pointery", `${percentY}%`);
                }
            };

            element.addEventListener('mousemove', element.$connector.updateMousePosition);
            element.addEventListener('mouseenter', element.$connector.updateMousePosition);

            console.log('Tech card initialized:', element);
        }
    }
};