package br.com.processor.order.controller;

import br.com.processor.order.dto.ErrorDTO;
import br.com.processor.order.entity.Order;
import br.com.processor.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "Permite buscar pedidos autenticando-se através JWT com sellerId no payload, filtrando por data inicial, data final, status do pedido, método de pagamento e status de pagamento.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca de pedidos bem-sucedida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a um dos seguintes motivos: somente uma das datas (inicial ou final) foi informada," +
                            " a data inicial é posterior à data final, ou um valor inválido foi fornecido para 'orderStatus', 'paymentMethod' ou 'paymentStatus'.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Autenticação falhou. Isso pode ocorrer se o JWT não for fornecido ou se for inválido.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno da aplicação. Entre em contato com o suporte técnico para mais informações.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
            })
    public List<Order> findOrdersWithOptionalFilters(@RequestHeader("Authorization") @Parameter(in = ParameterIn.HEADER, description = "JWT usado para autenticação. Deve conter o sellerId no payload.",
                                                             schema = @Schema(type = "string"),
                                                             example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWxsZXJJZCI6IjY2MDgxMDg0OGE3NTRlMGMzMmQ2Y2EyYSJ9.Me30gMGf-T15zr6FoPRLc6QnPT2KDn7JFA4Rg0Jte0c")
                                                        String auth,
                                                     @RequestParam(required = false) @Parameter(description = "Informar data inicial para filtrar todos os pedidos que estão dentro do período.",
                                                             schema = @Schema(type = "DateTime"), example = "02/04/2024")
                                                        LocalDate startDate,
                                                     @RequestParam(required = false) @Parameter(description = "Informar data final para filtrar todos os pedidos que estão dentro do período.",
                                                             schema = @Schema(type = "DateTime"), example = "03/04/2024")
                                                        LocalDate endDate,
                                                     @RequestParam(required = false) @Parameter(description = "Informar status do pedido para filtrar somente pedidos com o status informado." +
                                                             " Ex: NEW, APPROVED, FINISHED ou CANCELLED",
                                                             example = "APPROVED")
                                                        String orderStatus,
                                                     @RequestParam(required = false) @Parameter(description = "Informar o método de pagamento do pedido para filtrar somente pedidos com" +
                                                             " o método de pagamento informado. Ex: CREDIT, DEBIT, GIFT_CARD ou OTHER",
                                                             example = "CREDIT")
                                                        String paymentMethod,
                                                     @RequestParam(required = false) @Parameter(description = "Informar o status do pagamento do pedido para filtrar somente pedidos com" +
                                                             " o status informado. Ex: PENDING, APPROVED ou REFUSED",
                                                             example = "APPROVED")
                                                        String paymentStatus) {
        return orderService.findOrdersWithOptionalFilters(auth, startDate, endDate, orderStatus, paymentMethod, paymentStatus);
    }
}
