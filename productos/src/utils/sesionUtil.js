// src/utils/sessionUtils.js

export const parseDataString = (str) => {
    const dataObj = {};
    const keyValuePairs = str.split('&');

    keyValuePairs.forEach(pair => {
        const [key, value] = pair.split('=');
        if (value.includes(',')) {
            dataObj[key] = value.split(',');
        } else if (value === 'null') {
            dataObj[key] = null;
        } else if (!isNaN(value)) {
            dataObj[key] = Number(value);
        } else {
            dataObj[key] = value;
        }
    });

    return dataObj;
};
