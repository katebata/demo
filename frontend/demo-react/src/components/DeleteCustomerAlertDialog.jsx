import {
    AlertDialog, AlertDialogBody,
    AlertDialogContent, AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Button,
    useDisclosure
} from "@chakra-ui/react";
import React from "react";
import {deleteCustomer} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";



export default function DeleteCustomerAlertDialog({customerId,fetchCustomers,customerName}) {

    const deleteCustomerById = async () => {
        try{
            await deleteCustomer(customerId);
            fetchCustomers();
            onClose();
            successNotification(
                "Customer deleted",
                `Customer #${customerId} ${customerName} deleted successfully`
            )
        }
        catch (error){
            errorNotification(
                error.name,
                error.response.data.message
            )
            onClose();
        }

    }

    const { isOpen, onOpen, onClose } = useDisclosure()

    const cancelRef = React.useRef()

    return (
        <>
            <Button size='sm' colorScheme='red' onClick={onOpen}>
                Delete Customer
            </Button>

            <AlertDialog
                isOpen={isOpen}
                leastDestructiveRef={cancelRef}
                onClose={onClose}
            >
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                            Delete Customer
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            Are you sure you want to delete {customerName}? You can't undo this action afterwards.
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>
                                Cancel
                            </Button>
                            <Button colorScheme='red' onClick={deleteCustomerById} ml={3}>
                                Delete
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </>
    )
}

