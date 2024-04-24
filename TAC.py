def main():
    input_str = input("Enter an expression: ")

    # Split the expression into tokens
    tokens = input_str.split()

    # Initialize the TAC instruction list
    tac_instructions = []

    # Initialize the temporary variable count
    temp_count = 1

    # Initialize the index for the current token
    i = 0

    # Loop through the tokens to generate TAC instructions
    while i < len(tokens):
        token = tokens[i]

        # If the token is an operator ('+', '-', '*', '/', '%')
        if token in ['+', '-', '*', '/', '%']:
            # Get the operands
            operand1 = tokens[i - 1]
            operand2 = tokens[i + 1]

            # Generate the TAC instruction and append it to the list
            tac_instructions.append(f"t{temp_count} = {operand1} {token} {operand2}")

            # Replace the operands with the temporary variable in the expression
            tokens[i - 1] = f"t{temp_count}"
            del tokens[i:i+2]

            # Increment the temporary variable count
            temp_count += 1

            # Adjust the index to skip the processed operands
            i -= 1

        # Increment the index to process the next token
        i += 1

    # Generate the final assignment instruction
    final_result = "result"
    if len(tokens) > 1:
        # If there are remaining tokens, concatenate them with the last temporary variable
        final_result = f"t{temp_count} = {' '.join(tokens)}"
        temp_count += 1

    # Print the TAC instructions
    print("Three Address Code (TAC):")
    for instruction in tac_instructions:
        print(instruction)
    print(final_result)


if __name__ == "__main__":
    main()