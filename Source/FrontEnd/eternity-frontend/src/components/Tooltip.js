
function Tooltip() {
    return (
      {
          sinh: {
            head:'Hyperbolic Sine Function ~ sinh(x)',
            description: 'Computes the sinh value of given input.',
            inputParams: 'x: input value (integer or decimal)',
            returns: 'Decimal Result for sinh value of x.',
            exceptions: 'NA',
          },
          arccos: {
            head:'Inverse Cosine Function ~ arccos(x)',
            description: 'Computes the arccos of given input.',
            inputParams: 'x: input value (integer or decimal)',
            returns: 'Decimal Result for cosine inverse angle of x (in radians).',
            exceptions: 'x must be in the range [-1,1].',
          },
          gamma: {
            head:'Gamma Function ~ gamma(z)',
            description: 'Computes the gamma function of a given positive number z.',
            inputParams: 'z: input value (integer or decimal)',
            returns: 'Decimal Result for the gamma operation.',
            exceptions: 'z must be greater than 0.',
          },
          log: {
            head:'Logarithm Function ~ log(value,base)',
            description: 'Computes the logarithm of value with respect to specified base.',
            inputParams: 'value: value for which logarithm is to be calculated (integer or decimal)\nbase: base for which logarithm is to be calculated (integer or decimal)',
            returns: 'Decimal Result for the logarithm.',
            exceptions: 'Value should always be greater than 0.\nBase should always be greater than 0 and not equal to 1.',
          },
          sqrt: {
            head:'Square root function ~ sqrt(x)',
            description: 'Computes the square root of the input.',
            inputParams: 'x: input value (integer or decimal)',
            returns: 'Decimal Result for the square root of x.',
            exceptions: 'x must be greater than or equal to 0.',
          },
          pow: {
            head:'Power Function ~ pow(x,y)',
            description: 'Computes the exponential value of x to the y.',
            inputParams: 'x: base of the exponential value (integer or decimal)\ny: exponent to which the base is raised (integer or decimal)',
            returns: 'Decimal Result of the exponentiation.',
            exceptions: 'When x is a negative decimal value, y must be an integer.',
          },
          sd: {
            head:'Standard Deviation ~ sd(value0,value1, ...,valueN)',
            description: 'Computes the standard deviation of the given values.',
            inputParams: 'values: input values for the calculation of their standard deviation. (integer or decimal)',
            returns: 'Decimal Result for the standard deviation.',
            exceptions: 'At least 2 input values are required.',
          },
          abx: {
            head:'Exponent Function ~a(b^x)',
            description: 'Computes the product of a multiplicand and a base raised to the power of an exponent.',
            inputParams: 'a: the value to be multiplied (multiplicand, integer or decimal)\nb: the base of the exponent (integer or decimal),x: the exponent (integer or decimal)',
            returns: 'Decimal Result for abx.',
            exceptions: 'When base b is a negative decimal value, x must be an integer.',
          },
          exp: {
            head:'Exponential function ~ e^x',
            description: 'Computes the value of eulers constant e raised to the exponent of x.',
            inputParams: 'x: the exponent to e (integer or decimal)',
            returns: 'Decimal result of the exponentiation.',
            exceptions: 'NA',
          }
      }
    );
  }
  
  export default Tooltip;