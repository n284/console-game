package sevens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import sevens.card.CardFactory;
import sevens.card.objects.Card;
import sevens.player.PlayerFactory;
import sevens.player.objects.CPUPlayer;
import sevens.player.objects.HumanPlayer;
import sevens.player.utilities.interfaces.Player;
import sevens.card.utilities.params.Mark;
import sevens.card.utilities.params.Number;
import utilities.Keyboard;
import utilities.MessageLoader;
import utilities.exceptions.SystemException;
import utilities.interfaces.Game;

/**
 * 七並べのクラス
 */
public class Sevens implements Game {
    /** プレイヤーを保持する */
    private List<Player> playerList;

    /** 順位を保持する */
    private HashMap<Integer, Player> order;

    /** 場を表す */
    private HashMap<Mark, Card[]> layout;

    /** 全カードのオブジェクトを保持する */
    private HashMap<Mark, HashMap<Number, Card>> allCards;

    /** 出せるカードを管理する */
    private List<Card> candidatingCardList;

    /**
     * 七並べを実行する
     * 
     * @param
     * @return
     * @throws SystemException 停止の必要があるエラーが発生した場合にスローする
     */
    @Override
    public void run() throws SystemException {
        System.out.println(MessageLoader.loadMessage("sevens.start"));
        this.initialize();
        System.out.println(MessageLoader.loadMessage("sevens.input.player.number"));
        this.playerList = PlayerFactory.createPlayer();
        System.out.println(MessageLoader.loadMessage("sevens.deal"));
        this.dealCard();
        System.out.println(MessageLoader.loadMessage("sevens.layout.initialize"));
        this.initializeLayout();

        while (!this.playerList.isEmpty()) {
            Iterator<Player> itr = this.playerList.iterator();
            while (itr.hasNext()) {
                Player player = itr.next();
                System.out.println();
                System.out.println(MessageLoader.loadMessage("sevens.turn", player.getName()));
                this.seekCandidatingCard();
                this.printLayout();
                Card pullOutCard = player.selectCard(this.candidatingCardList);

                if (pullOutCard == null) {
                    if (player.getPassNum() == 0) {
                        for (Card card : player.getHand()) {
                            if (!card.equals(this.allCards.get(Mark.JOKER).get(Number.JOKER))) {
                                this.layout.get(card.getMark())[card.getNumber().getValue() - 1] = card;
                            }
                        }
                        player.getHand().clear();
                    } else if (player.getPassNum() < 0) {
                        throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
                    }
                } else if (pullOutCard.equals(this.allCards.get(Mark.JOKER).get(Number.JOKER))) {
                    if (player instanceof HumanPlayer) {
                        System.out.println(MessageLoader.loadMessage("sevens.select.joker"));
                        for (Card card : this.candidatingCardList) {
                            System.out.print(card.getMark().getValue() + "\t");
                        }
                        System.out.println();
                        for (Card card : this.candidatingCardList) {
                            System.out.print(card.getNumber().getValue() + "\t");
                        }
                        System.out.println();
                        for (int i = 1; i <= this.candidatingCardList.size(); i++) {
                            System.out.print(i + "\t");
                        }
                        System.out.println();
                        int index = Keyboard.inputInt(1, this.candidatingCardList.size());
                        this.layout.get(pullOutCard.getMark())[index] = pullOutCard;
                    } else {
                        System.out.println(MessageLoader.loadMessage("sevens.select.joker"));
                        int index = new Random().nextInt(0, this.candidatingCardList.size());
                        this.layout.get(pullOutCard.getMark())[index] = pullOutCard;
                    }
                } else {
                    if (this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1] != null &&
                            this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1]
                                    .equals(this.allCards.get(Mark.JOKER).get(Number.JOKER))) {
                        player.getHand().add(
                                this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1]);
                    }
                    this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1] = pullOutCard;
                }
                if (player.isFinished()) {
                    this.order.put(this.playerList.size(), player);
                    itr.remove();
                    if (this.playerList.size() == 1) {
                        this.order.put(1, this.playerList.get(0));
                    }
                }
            }
        }
        this.printLayout();
        this.printOrder();
    }

    /**
     * 順位を表示する
     * 
     * @param
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return
     */
    private void printOrder() throws SystemException {
        for (int i = 1; i <= this.order.size(); i++) {
            System.out.println(MessageLoader.loadMessage("sevens.winner", i, this.order.get(i).getName()));
        }
        System.out.println();
    }

    /**
     * 出せるカードを探す
     * 
     * @param
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    private void seekCandidatingCard() throws SystemException {
        try {
            Card joker = this.allCards.get(Mark.JOKER).get(Number.JOKER);
            if (!this.candidatingCardList.contains(joker)) {
                this.candidatingCardList.add(joker);
            }
            for (Mark mark : Mark.values()) {
                if (!mark.equals(Mark.JOKER)) {
                    for (int left = 6; left >= 0; left--) {
                        Card card = this.allCards.get(mark).get(Number.getEnum(left + 1));
                        if (this.layout.get(mark)[left] == null) {
                            if (!this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.add(card);
                            }
                            break;
                        } else if (this.layout.get(mark)[left].equals(joker)) {
                            if (!this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.add(card);
                                this.candidatingCardList.remove(joker);
                            }
                        } else {
                            if (this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.remove(card);
                            }
                        }
                    }
                    for (int right = 6; right <= 12; right++) {
                        Card card = this.allCards.get(mark).get(Number.getEnum(right + 1));
                        if (this.layout.get(mark)[right] == null) {
                            if (!this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.add(card);
                            }
                            break;
                        } else if (this.layout.get(mark)[right].equals(joker)) {
                            if (!this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.add(card);
                                this.candidatingCardList.remove(joker);
                            }
                        } else {
                            if (this.candidatingCardList.contains(card)) {
                                this.candidatingCardList.remove(card);
                            }
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException | UnsupportedOperationException | ClassCastException | NullPointerException
                | IllegalArgumentException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }

    /**
     * 場を表示する
     * 
     * @param
     * @return
     */
    private void printLayout() {
        for (Mark mark : this.layout.keySet()) {
            if (!mark.equals(Mark.JOKER)) {
                System.out.print(mark.getValue());
                for (Card card : this.layout.get(mark)) {
                    if (card == null) {
                        System.out.print("\t□");
                    } else {
                        System.out.print("\t" + card.getNumber().getValue());
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * ゲームを初期化する
     * 
     * @param
     * @return
     */
    private void initialize() {
        this.playerList = new ArrayList<>();
        this.candidatingCardList = new ArrayList<>();
        this.order = new HashMap<>();
        this.layout = new HashMap<>();
        this.allCards = new HashMap<>();

        for (Mark mark : Mark.values()) {
            this.layout.put(mark, new Card[13]);
            this.allCards.put(mark, new HashMap<>());
        }
    }

    /**
     * 場を初期化する
     * 
     * @param
     * @return
     * @throws SystemException
     */
    private void initializeLayout() throws SystemException {
        try {
            for (Player player : this.playerList) {
                List<Card> hand = player.getHand();
                for (int i = 0; i < hand.size(); i++) {
                    Card card = hand.get(i);
                    if (card.getNumber().equals(Number.SEVEN)) {
                        Mark mark = Mark.getEnum(card.getMark().getValue());
                        switch (mark) {
                            case SPADE:
                                this.layout.get(mark)[6] = card;
                                break;
                            case CLUB:
                                this.layout.get(mark)[6] = card;
                                break;
                            case HEART:
                                this.layout.get(mark)[6] = card;
                                break;
                            case DAIAMOND:
                                this.layout.get(mark)[6] = card;
                                break;
                            default:
                                throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
                        }
                        hand.remove(i);
                    }
                }

            }
        } catch (IndexOutOfBoundsException | UnsupportedOperationException | ClassCastException
                | NullPointerException | IllegalArgumentException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }

    /**
     * トランプをプレイヤーに配る
     * 
     * @param
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    private void dealCard() throws SystemException {
        try {
            List<Card> deck = CardFactory.getDeck();
            List<List<Card>> handList = new ArrayList<>();

            for (Card card : deck) {
                this.allCards.get(card.getMark()).put(card.getNumber(), card);
            }
            for (int i = 0; i < this.playerList.size(); i++) {
                handList.add(new ArrayList<>());
            }
            while (deck.size() > 0) {
                for (List<Card> hand : handList) {
                    hand.add(deck.get(0));
                    deck.remove(0);
                    if (deck.size() == 0) {
                        break;
                    }
                }
            }
            Collections.shuffle(handList);
            for (int i = 0; i < this.playerList.size(); i++) {
                this.playerList.get(i).setHand(handList.get(i));
            }
        } catch (IndexOutOfBoundsException | UnsupportedOperationException | ClassCastException
                | NullPointerException | IllegalArgumentException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }
}