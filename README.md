# 207-PROJECT

## Team Members
* Ethan Chiu (ethanch1u)
* Jackie Liang (jackieliang5985)
* Sudharshan Palaniyappan (sudharshanpal)
* Ujjvel Lijo (Ujivl)
* Eric Lee (itsyaboieric)

**Mind Map Board Generator**

**Project Overview**
* The Mind Map Board Generator is a virtual mind-mapping application designed to help users create, connect, and organize ideas through a graphical interface. The simulator allows users to add draggable text and commonImage nodes, connect them with lines, categorize them by color, and save and load mind maps for later use. This project aims to provide a seamless and intuitive experience for brainstorming, planning, and organizing information visually.

**User Stories**

**Creating, Saving, Loading Mind Maps:**
* User Story: John opens new mind map, saves the current mind map and later loads it to continue brainstorming.
* Assigned to: Team
* Use Case: The user can open a new mind map and then save the current state of the mind map, including nodes and connections, and reload it to continue working in the future.

* Interactor: Handles save/load requests.
* Controller: Manages the state of the mind map data.
* Presenter: Loads and displays the saved mind map data on request.

**Adding Text-Based Nodes:**
* User Story: John wants to make draggable text nodes and position it on the mind map board.
* Assigned to: Eric
* Use Case: The user makes a draghable text node and position text nodes by holding left click button on the node to move around the board to organize their mind map visually.

* Interactor: Listens for user input to create text nodes.
* Controller: Manages node creation and positioning.
* Presenter: Displays text nodes at specified positions on the board.

**Adding Elements Within Mind Map:**
* User Story: John uploads an commonImage as a draggable node on the board.
* Assigned to: Sudharshan
* Use Case: The user selects an commonImage from their device, which appears as a draggable node on the board, allowing the inclusion of visual references in the mind map.

* Interactor: Manages file selection and commonImage upload.
* Controller: Adds the commonImage node to the board.
* Presenter: Displays the commonImage at a chosen location.

**Connecting Nodes with Lines:**
* User Story: John wants to have a connected text node that connects to node that already exists in the mind map
* Assigned to: Eric
* Use Case: The user can choose to create a new text node that is connected to a parent node by right clicking on the parent node and create a new connected node

* Interactor: Detects starting and ending points for line creation.
* Controller: Establishes the line connection between nodes.
* Presenter: Renders lines with specified colors between nodes.

**Deleting Nodes:**
* User Story: John removes irrelevant nodes which automatically disconnects all the lines that connect the node to others.
* Assigned to: Sudharsian
* Use Case: The user right click nodes to delete it, enabling easy organization and removal of irrelevant information.

* Interactor: Detects dragging motions to the trash icon.
* Controller: Deletes the node from the board.
* Presenter: Removes the node from the display.

**Categorizing Nodes by Color:**
* User Story: John assigns colors to nodes and lines to group related ideas or indicate importance.
* Assigned to: Ethan
* Use Case: The user customizes the color of nodes and lines to categorize or prioritize information visually.

* Interactor: Receives user-selected colors for nodes or lines.
* Controller: Applies color changes to selected nodes or lines.
* Presenter: Updates the display with the chosen colors.

**Resizing Nodes: Optional**


