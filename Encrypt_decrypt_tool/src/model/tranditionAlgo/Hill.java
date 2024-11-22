package model.tranditionAlgo;

import java.util.Map;

public class Hill extends ATraditionModel {

    // Encrypts the message using the key matrix
    public String encrypt(String message, int[][] keyMatrix) {
        message = message.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherText = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] cipherVector = new int[matrixSize];
        int index = 0;

        while (index < message.length()) {
            // Prepare the message vector by filling it with characters from the message
            for (int i = 0; i < matrixSize; i++) {
                if (index < message.length()) {
                    messageVector[i] = message.charAt(index++) - 'A';
                } else {
                    messageVector[i] = 'X' - 'A'; // Padding with 'X' if needed
                }
            }

            // Multiply the key matrix by the message vector
            for (int i = 0; i < matrixSize; i++) {
                cipherVector[i] = 0;
                for (int j = 0; j < matrixSize; j++) {
                    cipherVector[i] += keyMatrix[i][j] * messageVector[j];
                }
                cipherVector[i] = cipherVector[i] % 26;  // Apply modulo 26
                cipherText.append((char) (cipherVector[i] + 'A'));
            }
        }

        return cipherText.toString();
    }

    // Decrypts the message using the inverse key matrix
    public String decrypt(String message, int[][] inverseKeyMatrix) {
        message = message.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainText = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] plainVector = new int[matrixSize];
        int index = 0;

        while (index < message.length()) {
            // Prepare the message vector by filling it with characters from the message
            for (int i = 0; i < matrixSize; i++) {
                if (index < message.length()) {
                    messageVector[i] = message.charAt(index++) - 'A';
                } else {
                    messageVector[i] = 'X' - 'A'; // Padding with 'X' if needed
                }
            }

            // Multiply the inverse key matrix by the message vector
            for (int i = 0; i < matrixSize; i++) {
                plainVector[i] = 0;
                for (int j = 0; j < matrixSize; j++) {
                    plainVector[i] += inverseKeyMatrix[i][j] * messageVector[j];
                }
                plainVector[i] = plainVector[i] % 26;  // Apply modulo 26
                plainText.append((char) (plainVector[i] + 'A'));
            }
        }

        return plainText.toString();
    }

    // Validates that the determinant of the key matrix is not zero modulo 26
    private void validateDeterminant(int[][] keyMatrix, int n) {
        int det = determinant(keyMatrix, n) % 26;
        if (det == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    // Computes the determinant of a matrix recursively
    private int determinant(int[][] matrix, int n) {
        int det = 0;
        if (n == 1) {
            return matrix[0][0];
        }
        int sign = 1;
        int[][] subMatrix = new int[n - 1][n - 1];
        for (int x = 0; x < n; x++) {
            int subI = 0;
            for (int i = 1; i < n; i++) {
                int subJ = 0;
                for (int j = 0; j < n; j++) {
                    if (j != x) {
                        subMatrix[subI][subJ++] = matrix[i][j];
                    }
                }
                subI++;
            }
            det += sign * matrix[0][x] * determinant(subMatrix, n - 1);
            sign = -sign;
        }
        return det;
    }

    // Find the inverse of a matrix modulo 26
    public int[][] inverse(int[][] keyMatrix) {
        int n = keyMatrix.length;
        int[][] inverse = new int[n][n];

        // Calculate the determinant of the matrix
        int det = determinant(keyMatrix, n);
        int detInverse = modInverse(det, 26);

        if (detInverse == -1) {
            throw new IllegalArgumentException("Matrix is not invertible.");
        }

        // For any n x n matrix, we calculate the adjugate (cofactor) matrix
        int[][] adjugate = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Get the cofactor matrix (minor matrix) for each element
                int[][] minor = getMinor(keyMatrix, i, j);
                adjugate[j][i] = (int) Math.pow(-1, i + j) * determinant(minor, n - 1) % 26;
                if (adjugate[j][i] < 0) {
                    adjugate[j][i] += 26; // Make sure the value is positive modulo 26
                }
            }
        }

        // Multiply adjugate by the modular inverse of the determinant
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (adjugate[i][j] * detInverse) % 26;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26; // Ensure all elements are positive modulo 26
                }
            }
        }

        return inverse;
    }

    // Find minor of a matrix by removing row i and column j
    private int[][] getMinor(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] minor = new int[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[r][c++] = matrix[i][j];
            }
            r++;
        }
        return minor;
    }

    // Find modular multiplicative inverse of a number modulo m
    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // No inverse if no solution
    }

    public static void main(String[] args) {
        Hill cipher = new Hill();

        // Example for a 3x3 key matrix
//        int[][] keyMatrix = {
//                {6, 24, 1},
//                {13, 16, 10},
//                {20, 17, 15}
//        };
//        2x2
        int[][] keyMatrix = {
                {11, 8},
                {3, 7}
        };

        // Encrypt a message
        String message = "DHNONGLAM";
        String encryptedMessage = cipher.encrypt(message, keyMatrix);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Get the inverse key matrix
        int[][] inverseKeyMatrix = cipher.inverse(keyMatrix);

        // Decrypt the message
        String decryptedMessage = cipher.decrypt(encryptedMessage, inverseKeyMatrix);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }

    @Override
    public void saveData(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
}
