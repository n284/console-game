package sevens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import sevens.card.CardFactory;
import sevens.card.objects.Card;
import sevens.player.PlayerFactory;
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
                int turnNum = 1;
                while (turnNum > 0) {
                    turnNum--;
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
                            throw new SystemException(MessageLoader.loadMessage("error.unknown"));
                        }
                    } else if (pullOutCard.equals(this.allCards.get(Mark.JOKER).get(Number.JOKER))) {
                        System.out.println(MessageLoader.loadMessage("sevens.select.joker"));

                        if (player instanceof HumanPlayer) {
                            for (Card card : this.candidatingCardList) {
                                System.out.print(String.format("%s\t", card));
                            }
                            System.out.println();
                            for (int i = 1; i <= this.candidatingCardList.size(); i++) {
                                System.out.print(String.format("%d\t", i));
                            }
                            System.out.println();
                            int index = Keyboard.inputInt(1, this.candidatingCardList.size()) - 1;
                            this.layout.get(this.candidatingCardList.get(index).getMark())[this.candidatingCardList
                                    .get(index).getNumber().getValue() - 1] = pullOutCard;
                        } else {
                            Card card = this.candidatingCardList
                                    .get(new Random().nextInt(0, this.candidatingCardList.size()));
                            this.layout.get(card.getMark())[card.getNumber().getValue() - 1] = pullOutCard;
                        }
                    } else {
                        if (this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1] != null &&
                                this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1]
                                        .equals(this.allCards.get(Mark.JOKER).get(Number.JOKER))) {
                            player.getHand().add(
                                    this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1]);
                            turnNum++;
                        }

                        this.layout.get(pullOutCard.getMark())[pullOutCard.getNumber().getValue() - 1] = pullOutCard;
                    }

                    if (player.isFinished()) {
                        if (player.getPassNum() == 0) {
                            for (int i = this.playerList.size() + this.order.size(); i >= 0; i--) {
                                if (this.order.get(i) == null) {
                                    this.order.put(i, player);
                                    break;
                                }
                            }
                        } else {
                            for (int i = 1; i <= this.playerList.size() + this.order.size(); i++) {
                                if (this.order.get(i) == null) {
                                    this.order.put(i, player);
                                    break;
                                }
                            }
                        }

                        itr.remove();

                        if (this.playerList.size() == 1) {
                            for (int i = 1; i <= this.playerList.size() + this.order.size(); i++) {
                                if (this.order.get(i) == null) {
                                    this.order.put(i, itr.next());
                                    itr.remove();
                                    break;
                                }
                            }
                        }
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
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    private void printOrder() throws SystemException {
        for (int i = 1; i <= this.order.size(); i++) {
            System.out.println(MessageLoader.loadMessage("sevens.winner", i, this.order.get(i)));
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
        Card joker = this.allCards.get(Mark.JOKER).get(Number.JOKER);
        List<Mark> markList = new ArrayList<>(Arrays.asList(Mark.values()));
        markList.remove(Mark.JOKER);

        if (!this.candidatingCardList.contains(joker)) {
            this.candidatingCardList.add(joker);
        }

        for (Mark mark : markList) {
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

    /**
     * 場を表示する
     * 
     * @param
     * @return
     */
    private void printLayout() {
        for (Mark mark : this.layout.keySet()) {
            for (Card card : this.layout.get(mark)) {
                if (card == null) {
                    System.out.print("\t□");
                } else {
                    System.out.print(String.format("\t%s", card));
                }
            }
            System.out.println();
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
            if (!mark.equals(Mark.JOKER)) {
                this.layout.put(mark, new Card[13]);
            }
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
        List<Card> sevenCardList = Arrays.asList(
                this.allCards.get(Mark.CLUB).get(Number.SEVEN),
                this.allCards.get(Mark.DAIAMOND).get(Number.SEVEN),
                this.allCards.get(Mark.HEART).get(Number.SEVEN),
                this.allCards.get(Mark.SPADE).get(Number.SEVEN));

        for (Player player : this.playerList) {
            Iterator<Card> itr = player.getHand().iterator();
            while (itr.hasNext()) {
                Card card = itr.next();
                if (sevenCardList.contains(card)) {
                    this.layout.get(card.getMark())[6] = card;
                    itr.remove();
                }
            }
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
        List<Card> deck = CardFactory.getDeck();
        List<List<Card>> handList = new ArrayList<>();

        for (Card card : deck) {
            this.allCards.get(card.getMark()).put(card.getNumber(), card);
        }
        for (int i = 0; i < this.playerList.size(); i++) {
            handList.add(new ArrayList<>());
        }
        while (!deck.isEmpty()) {
            for (List<Card> hand : handList) {
                hand.add(deck.get(0));
                deck.remove(0);
                if (deck.isEmpty()) {
                    break;
                }
            }
        }

        Collections.shuffle(handList);

        for (Player player : this.playerList) {
            player.setHand(handList.get(0));
            handList.remove(0);
        }
    }
}