const groceryItem = (state, action) => {
    switch (action.type) {
        case 'ADD_GROCERY_ITEM':
            return {
                id: action.id,
                text: action.text,
                completed: false,
            };
        case 'TOGGLE_GROCERY_ITEM':
            if (state.id !== action.id) {
                return state;
            }
            return {
                ...state,
                completed: !state.completed,
            };
        default:
            return state;
    }
};

const groceryList = (state = [], action) => {
  switch (action.type) {
    case 'ADD_GROCERY_ITEM':
      return [
        ...state,
        groceryItem(undefined, action),
      ];
    case 'TOGGLE_GROCERY_ITEM':
      return state.map(t =>
        groceryItem(t, action)
      );
    default:
      return state;
  }
};

export default groceryList;
