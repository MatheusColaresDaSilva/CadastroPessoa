import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

const Cadastro = () => {
    const useStyles = makeStyles((theme) => ({
        root: {
            flexGrow: 1,
          },
          paper: {
            padding: theme.spacing(2),
            textAlign: 'center',
            color: 'transparent'
          },
    }));

    const [name, setName] = React.useState();
    const [email, setEmail] = React.useState();
    const classes = useStyles();

    const handleChange = (event) => {
        setName(event.target.value);
      };
    
    

    return (
        <React.Fragment>
            <CssBaseline />
            <Container maxWidth="sm">
                    <form className="cadastro" noValidate autoComplete="off">
                    <div className={classes.root}>
                        <Grid container spacing={3} mx={4}>
                            <Grid item xs={12}>
                            <Paper className={classes.paper}>
                                <FormControl variant="outlined">
                                    <InputLabel htmlFor="component-outlined">Nome</InputLabel>
                                    <OutlinedInput id="component-outlined" value={name} onChange={handleChange} label="Name" />
                                </FormControl>
                            </Paper>
                            <Paper className={classes.paper}>
                                <FormControl variant="outlined">
                                    <InputLabel htmlFor="component-outlined">Email</InputLabel>
                                    <OutlinedInput id="component-outlined" value={email} onChange={handleChange} label="Email" />
                                </FormControl>
                            </Paper>
                            </Grid>
                        </Grid>
                    </div>
                        
                    </form>
            </Container>
        </React.Fragment>

    );
};

export default Cadastro;
/*import React, { Component } from 'react';

import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Input from '@material-ui/core/Input';


class Cadastro extends Component {
    render() {
        return (
            <React.Fragment>
                <CssBaseline />
                <Container maxWidth="sm">
                    <Typography component="div" style={{ backgroundColor: '#dde3d3', height: '100vh' }}>
                        <form className="cadastro" noValidate autoComplete="off">
                        <FormControl>
                            <InputLabel htmlFor="component-simple">Name</InputLabel>
                            <Input id="component-simple" value={name} onChange={handleChange} />
                        </FormControl>
                        </form>
                    </Typography>
                </Container>
            </React.Fragment>

            /*<form className="cadastro" noValidate autoComplete="off">
                <TextField
                id="nome"
                label="Label"
                style={{ margin: 8 }}
                placeholder="Nome"
                fullWidth
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                }}
                />
                 <Input placeholder="Nome" inputProps={{ 'aria-label': 'description' }} />
            </form>
        );
    }
}

export default Cadastro;*/