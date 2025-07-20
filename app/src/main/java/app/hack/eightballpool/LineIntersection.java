package app.hack.eightballpool;

public class LineIntersection {
    public float endX;
    public float endY;

    public int wallHit;

    public LineIntersection(float endX, float endY, int wallHit) {
        this.endX = endX;
        this.endY = endY;
        this.wallHit = wallHit;
    }

    public static LineIntersection getLineIntersectionPoint(float startX, float startY, float angle, int canvasWidth, int canvasHeight, float ballRadius) {
        double radAngle = Math.toRadians(angle);

        /* Calcula os componentes do vetor de direção. O eixo Y do Android aumenta para baixo,
         * então um ângulo positivo significa um dy negativo
         */
        float dx = (float) Math.cos(radAngle); // componente X do vetor de direção
        float dy = (float) -Math.sin(radAngle); // componente Y do vetor de direção

        /* Inicializa 'distanceParameter' com o valor máximo possível de um float
         * Este parâmetro representa a distância ao longo da linha até o ponto de interseção mais próximo
         * Começa com um valor muito alto para garantir que qualquer interseção válida será menor
         */
        float distanceParameter = Float.MAX_VALUE;

        int wall = 0; // Parede atingida ( 0: nenhuma )

        // Posições das paredes considerando o raio da bola
        float effectiveLeftWall = 0 + ballRadius;
        float effectiveRightWall = canvasWidth - ballRadius;
        float effectiveTopWall = 0 + ballRadius;
        float effectiveBottomWall = canvasHeight - ballRadius;

        // -0.001f ( considera uma pequena tolerância para erros de ponto flutuante )

        // Verifica se a bola está se movendo para a esquerda
        if (dx < 0) {
            // Calcula o 'distanceParameter' para a interseção com a parede esquerda
            float distanceParameter_left = (effectiveLeftWall - startX) / dx;

            // Verifica se a interseção com a parede esquerda é válida e a mais próxima
            if (distanceParameter_left >= -0.001f && distanceParameter_left < distanceParameter) {
                // Calcula a coordenada Y do ponto de interseção na parede esquerda
                float intersectY = startY + distanceParameter_left * dy;
                // Verifica se a interseção ocorre dentro dos limites Y das paredes efetivas
                if (intersectY >= effectiveTopWall && intersectY <= effectiveBottomWall) {
                    // Atualiza 'distanceParameter' para a distância da parede esquerda
                    distanceParameter = distanceParameter_left;
                    wall = 3; // Esquerda
                }
            }
        }

        // Verifica se a bola está se movendo para a direita
        if (dx > 0) {
            // Calcula o 'distanceParameter' para a interseção com a parede direita
            float distanceParameter_right = (effectiveRightWall - startX) / dx;

            // Verifica se a interseção com a parede direita é válida e a mais próxima
            if (distanceParameter_right >= -0.001f && distanceParameter_right < distanceParameter) {
                // Calcula a coordenada Y do ponto de interseção na parede direita
                float intersectY = startY + distanceParameter_right * dy;
                // Verifica se a interseção ocorre dentro dos limites Y das paredes efetivas
                if (intersectY >= effectiveTopWall && intersectY <= effectiveBottomWall) {
                    // Atualiza 'distanceParameter' para a distância da parede direita
                    distanceParameter = distanceParameter_right;
                    wall = 4; // Direita
                }
            }
        }

        // Verifica se a bola está se movendo para cima
        if (dy < 0) {
            // Calcula o 'distanceParameter' para a interseção com a parede superior
            float distanceParameter_top = (effectiveTopWall - startY) / dy;

            // Verifica se a interseção com a parede superior é válida e a mais próxima
            if (distanceParameter_top >= -0.001f && distanceParameter_top < distanceParameter) {
                // Calcula a coordenada X do ponto de interseção na parede superior
                float intersectX = startX + distanceParameter_top * dx;
                // Verifica se a interseção ocorre dentro dos limites X das paredes efetivas
                if (intersectX >= effectiveLeftWall && intersectX <= effectiveRightWall) {
                    // Atualiza 'distanceParameter' para a distância da parede superior
                    distanceParameter = distanceParameter_top;
                    wall = 1; // Superior
                }
            }
        }

        // Verifica se a bola está se movendo para baixo
        if (dy > 0) {
            // Calcula o 'distanceParameter' para a interseção com a parede inferior
            float distanceParameter_bottom = (effectiveBottomWall - startY) / dy;

            // Verifica se a interseção com a parede inferior é válida e a mais próxima
            if (distanceParameter_bottom >= -0.001f && distanceParameter_bottom < distanceParameter) {
                // Calcula a coordenada X do ponto de interseção na parede inferior
                float intersectX = startX + distanceParameter_bottom * dx;
                // Verifica se a interseção ocorre dentro dos limites X das paredes efetivas
                if (intersectX >= effectiveLeftWall && intersectX <= effectiveRightWall) {
                    // Atualiza 'distanceParameter' para a distância da parede inferior
                    distanceParameter = distanceParameter_bottom;
                    wall = 2; // Inferior
                }
            }
        }

        // Se 'distanceParameter' ainda é o valor máximo, significa que não houve interseção
        if (distanceParameter == Float.MAX_VALUE) {
            /* Define 'distanceParameter' para um valor grande ( o maior entre largura e altura do canvas )
             * para que o ponto final seja calculado em uma distância razoável, mesmo sem uma colisão
             */
            distanceParameter = Math.max(canvasWidth, canvasHeight);
        }

        float endX = startX + distanceParameter * dx;
        float endY = startY + distanceParameter * dy;

        return new LineIntersection(endX, endY, wall);
    }
}
