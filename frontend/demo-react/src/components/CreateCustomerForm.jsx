import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, FormLabel, Input, Select, Button, Stack} from "@chakra-ui/react";
import {saveCustomer} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";

const MyTextInput = ({ label, ...props }) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};



const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const CreateCustomerForm = ({fetchCustomers}) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    age: 0,
                    gender: '',
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .required('Required'),
                    email: Yup.string()
                        .email('Must be an email')
                        .required('Required'),
                    age: Yup.number()
                        .min(16,'Must be at least 16 years old')
                        .required('Required'),
                    gender: Yup.string()
                        .required('Required')
                        .oneOf(['MALE','FEMALE'], 'Invalid gender'),
                })}
                onSubmit={(customer, { setSubmitting }) => {
                    setSubmitting(true);
                    saveCustomer(customer)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Customer saved",
                                `${customer.name} was successfully saved`
                            )
                            fetchCustomers();
                        }).catch(err => {
                            console.log(err);
                            errorNotification(
                                "Customer creation failed",
                                err.response.data.message
                            )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid,isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />

                            <MyTextInput
                                label="Email"
                                name="email"
                                type="email"
                                placeholder="jane@gmail.com"
                            />

                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="24"
                            />

                            <MySelect label="Gender" name="gender">
                                <option value="">Select gender</option>
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>

                            </MySelect>

                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateCustomerForm;