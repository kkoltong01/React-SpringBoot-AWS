import React,{useState} from "react";
import { ListItem, ListItemText, InputBase, ListItemSecondaryAction, IconButton, Checkbox} from "@material-ui/core";
import { DeleteOutline } from "@material-ui/icons";

const Todo = (props) => {
    const [item, setItem] = useState(props.item);
    const [readOnly, setReadOnly] = useState(true);
    const editItem = props.editItem;
    const deleteItem = props.deleteItem;

    const turnOffReadOnly = () => {
        setReadOnly(false);
    };

    const turnOnReadOnly = (e) => {
        if(e.key === 'Enter') {
            setReadOnly(true);
        }
    };

    const editItemEventHandler = (e) => {
        item.title=e.target.value;
        editItem();
    }

    const deleteItemEventHandler = () => {
        deleteItem(item);
    };

    const checkboxEventHandler = (e) => {
        item.done = e.target.checked;
        editItem();
        console.log(e.target.checked);
    }


    return (
        <ListItem>
            <Checkbox checked={item.done}
                onChange={checkboxEventHandler} />
            <ListItemText>
                <InputBase
                    inputProps={{"aria-label" : "naked", readOnly : readOnly}}
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnReadOnly}
                    onChange={editItemEventHandler}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo"
                    onClick={deleteItemEventHandler}>
                    <DeleteOutline/>
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;