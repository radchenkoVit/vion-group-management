function toKebabCase(str) {
    return str && str
        .match(/[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]|[0-9]+/g)
        .map(x => x.toLowerCase())
        .join('-');
}

module.exports = [{
    type: 'select',
    name: 'type',
    message: 'What type of component do you want to create?',
    choices: ['atom', 'molecule', 'organism', 'unique-organism', 'template', 'page']
}, {
    type: 'input',
    name: 'name',
    message: 'Type component name(without prefix). Example: remote-select or RemoteSelect',
    validate(value) {
        const fileName = toKebabCase(value);
        const prefix = fileName.substring(0, 2);
        const forbiddenPrefixes = ['a-', 'm-', 'o-', 't-', 'p-'];
        if (!value.length) {
            return 'Components should have a name!!!';
        }
        if (value.length < 4) {
            return 'Component name should contain at least 4 symbols!!!';
        }
        if (forbiddenPrefixes.includes(prefix)) {
            return 'No prefixes allowed!!! Prefix will be added automatically based on component type!!!';
        }
        return true;
    }
}];
