# 207-PROJECT

## Team Members
* Ethan Chiu (ethanch1u)
* Jackie Liang (jackieliang5985)
* Sudharshan Palaniyappan (sudharshanpal)
* Ujjvel Lijo (Ujivl)
* Eric Lee (itsyaboieric)

Mind Map Board Generator
Project Overview
The Mind Map Board Generator is a virtual mind-mapping application designed to help users create, connect, and organize ideas through a graphical interface. The simulator allows users to add draggable text and image nodes, connect them with lines, categorize them by color, and save and load mind maps for later use. This project aims to provide a seamless and intuitive experience for brainstorming, planning, and organizing information visually.

User Stories
Each team member is responsible for implementing at least one user story to ensure collaborative development. Below are the fully documented user stories:

Adding Text-Based Nodes
User Story: John creates a text node, types in an idea, and positions it on the mind map board.
Assigned to: Team (General)
Use Case: The user can create and position text nodes by typing in content and dragging nodes around the board to organize their mind map visually.
Interactor, Controller, and Presenter:

Interactor: Listens for user input to create text nodes.
Controller: Manages node creation and positioning.
Presenter: Displays text nodes at specified positions on the board.
Adding Image-Based Nodes
User Story: John uploads an image as a draggable node on the board.
Assigned to: Sudharshan
Use Case: The user selects an image from their device, which appears as a draggable node on the board, allowing the inclusion of visual references in the mind map.
Interactor, Controller, and Presenter:

Interactor: Manages file selection and image upload.
Controller: Adds the image node to the board.
Presenter: Displays the image at a chosen location.
Connecting Nodes with Lines
User Story: John draws a colored line between two nodes to represent relationships.
Assigned to: Ujivel
Use Case: The user can connect nodes using colored lines, visually representing relationships between ideas or topics.
Interactor, Controller, and Presenter:

Interactor: Detects starting and ending points for line creation.
Controller: Establishes the line connection between nodes.
Presenter: Renders lines with specified colors between nodes.
Deleting Nodes
User Story: John removes irrelevant nodes by dragging them to a trash icon.
Assigned to: Jackie
Use Case: The user drags nodes to a trash icon to delete them, enabling easy organization and removal of irrelevant information.
Interactor, Controller, and Presenter:

Interactor: Detects dragging motions to the trash icon.
Controller: Deletes the node from the board.
Presenter: Removes the node from the display.
Categorizing Nodes by Color
User Story: John assigns colors to nodes and lines to group related ideas or indicate importance.
Assigned to: Ethan
Use Case: The user customizes the color of nodes and lines to categorize or prioritize information visually.
Interactor, Controller, and Presenter:

Interactor: Receives user-selected colors for nodes or lines.
Controller: Applies color changes to selected nodes or lines.
Presenter: Updates the display with the chosen colors.
Saving and Loading Mind Maps
User Story: John saves the current mind map and later loads it to continue brainstorming.
Assigned to: Eric
Use Case: The user can save the current state of the mind map, including nodes and connections, and reload it to continue working in the future.
Interactor, Controller, and Presenter:

Interactor: Handles save/load requests.
Controller: Manages the state of the mind map data.
Presenter: Loads and displays the saved mind map data on request.
